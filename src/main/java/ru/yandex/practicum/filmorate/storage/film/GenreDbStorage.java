package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Primary
@Repository
@Slf4j
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addGenres(Film film) {
        String sqlQuery = "INSERT INTO genres (film_id, genre_id) VALUES(?, ?)";
        List<Genre> genres = new ArrayList<>(film.getGenres());
        jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, film.getId());
                preparedStatement.setInt(2, genres.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return genres.size();
            }
        });
        log.info("Добавлены жанры для фильма: {} {}", film.getId(), genres);
    }

    @Override
    public void updateGenres(Film film) {
        String sqlQuery = "DELETE FROM genres WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
        addGenres(film);
    }

    @Override
    public List<Genre> getAllGenres() {
        String sqlQuery = "SELECT id, genre_name FROM genre";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public List<Genre> getFilmGenres(Film film) {
        String sqlQuery = "SELECT g.id, gs.genre_id, g.genre_name " +
                "FROM genres gs " +
                "LEFT JOIN genre g ON gs.genre_id = g.id " +
                "WHERE gs.film_id = ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre, film.getId());
    }

    @Override
    public Genre getGenreById(Integer id) {
        String sqlQuery = "SELECT id, genre_name " +
                "FROM genre " +
                "WHERE id = ?";
        Genre genre;
        try {
            genre = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Жанр с id {} не найден", id);
            throw new NotFoundException("Жанр с id " + id + " не найден");
        }
        return genre;
    }

    @Override
    public List<Film> getListGenresForListFilms(List<Film> listFilms) {
        List<String> filmIds = new ArrayList<>();
        for (Film film : listFilms) {
            filmIds.add(String.valueOf(film.getId()));
        }
        String csv = String.join(",", filmIds);

        String sqlQuery = "SELECT gs.film_id, g.id, gs.genre_id, g.genre_name " +
                "FROM genres gs " +
                "LEFT JOIN genre g ON gs.genre_id = g.id " +
                "WHERE gs.film_id IN (" + csv + ")";
        Map<Long, Set<Genre>> genresMap = jdbcTemplate.query(sqlQuery, this::mapRowToGenresMap);

        if (genresMap != null) {
            for (Film film : listFilms) {
                if (genresMap.containsKey(film.getId())) {
                    film.setGenres(genresMap.get(film.getId()));
                } else {
                    film.setGenres(new HashSet<>());
                }
            }
        }
        return listFilms;
    }

    private Map<Long, Set<Genre>> mapRowToGenresMap(ResultSet resultSet) throws SQLException {

        Map<Long, Set<Genre>> genresMap = new HashMap<>();
        while (resultSet.next()) {
            Long filmId = resultSet.getLong("film_id");
            if (!genresMap.containsKey(filmId)) {
                genresMap.put(filmId, new HashSet<>());
            }

            Genre genre = new Genre(resultSet.getInt("genre_id"), resultSet.getString("genre_name"));
            genresMap.get(filmId).add(genre);
        }
        return genresMap;
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("genre_name"))
                .build();
    }
}
