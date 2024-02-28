package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Primary
@Repository
@Slf4j
public class LikesDbStorage implements LikesStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikesDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        String sqlQuery = "INSERT INTO likes (film_id, user_id) VALUES(?, ?)";
        int recordsAffected = jdbcTemplate.update(sqlQuery, filmId, userId);
        if (recordsAffected == 0) {
            log.warn("Пользователь с id = {} ставит лайк фильму с id = {}.  Пользователь или фильм не найден",
                    userId,
                    filmId);
            throw new NotFoundException("Добавление лайка "
                    + userId + " -> " + filmId
                    + ". Пользователь или фильм не найден");
        }
        log.info("Пользователь id={} лайкнул фильм id={}", userId, filmId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        String sqlQuery = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        int recordsAffected = jdbcTemplate.update(sqlQuery, filmId, userId);
        if (recordsAffected == 0) {
            log.warn("Пользователь с id = {} отзывает лайк у фильма с id = {}.  Пользователь или фильм не найден",
                    userId,
                    filmId);
            throw new NotFoundException("Отзыв лайка "
                    + userId + " -> " + filmId
                    + ". Пользователь или фильм не найден");
        }
        log.info("Удален лайк: userid = {} -> filmid = {}", userId, filmId);
    }

    @Override
    public List<Film> listPopularFilms(int count) {
        String sqlQuery = "SELECT fs.*, m.mpa_name " +
                "FROM films fs " +
                "LEFT JOIN MPA m ON fs.mpa_id = m.id " +
                "WHERE fs.id IN " +
                "(" +
                "SELECT fs.id FROM films fs " +
                "LEFT JOIN likes ls ON ls.FILM_ID = FS.ID " +
                "GROUP BY ls.film_id, fs.id " +
                "ORDER BY COUNT(ls.film_id) DESC " +
                "LIMIT ?" +
                ")";
        return jdbcTemplate.query(sqlQuery, MapRowToFilm::mapRow, count);
    }
}
