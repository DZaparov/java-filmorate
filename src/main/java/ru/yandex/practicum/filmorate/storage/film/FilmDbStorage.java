package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Primary
@Repository
@Slf4j
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> listFilms() {
        String sqlQuery = "SELECT fs.*, m.mpa_name " +
                "FROM FILMS fs " +
                "LEFT JOIN MPA m ON fs.mpa_id = m.id ";
        return jdbcTemplate.query(sqlQuery, new MapRowToFilm());
    }

    @Override
    public Film createFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue());
        log.info("Создан фильм: {}", film);
        return film;
    }

    @Override
    public Film getFilmById(Long id) {
        String sqlQuery = "SELECT fs.*, m.mpa_name " +
                "FROM FILMS fs " +
                "LEFT JOIN MPA m ON fs.mpa_id = m.id " +
                "WHERE fs.id = ?";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(sqlQuery, new MapRowToFilm(), id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Фильм с id {} не найден", id);
            throw new NotFoundException("Фильм с id " + id + " не найден");
        }
        log.info("Получен фильм: {}", film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "UPDATE films SET " +
                "name = ?, description = ?, releaseDate = ?, duration = ?, mpa_id = ? " +
                "WHERE id = ?";

        int recordsAffected = jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getReleaseDate()
                , film.getDuration()
                , film.getMpa().getId()
                , film.getId()
        );
        if (recordsAffected == 0) {
            log.warn("Обновление фильма - Фильм с id {} не найден", film.getId());
            throw new NotFoundException("Фильм с id " + film.getId() + " не найден");
        }
        return film;
    }
}
