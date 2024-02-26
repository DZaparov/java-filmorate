package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest // указываем, о необходимости подготовить бины для работы с БД
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FilmDbStorageTest {
    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testGetFilmById() {
        // Подготавливаем данные для теста
        Film newFilm = new Film(1L,
                "Фильм",
                "Описание",
                LocalDate.of(2000, 12, 12),
                26,
                new Mpa(1, "G"),
                new HashSet<>());
        FilmDbStorage filmStorage = new FilmDbStorage(jdbcTemplate);
        filmStorage.createFilm(newFilm);

        // вызываем тестируемый метод
        Film savedFilm = filmStorage.getFilmById(1L);

        // проверяем утверждения
        assertThat(savedFilm)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(newFilm);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testListFilms() {
        // Подготавливаем данные для теста
        Film newFilm = new Film(1L,
                "Фильм",
                "Описание",
                LocalDate.of(2000, 12, 12),
                26,
                new Mpa(1, "G"),
                new HashSet<>());
        Film newFilm2 = new Film(2L,
                "Фильм2",
                "Описание2",
                LocalDate.of(2000, 12, 12),
                26,
                new Mpa(1, "G"),
                new HashSet<>());
        FilmDbStorage filmStorage = new FilmDbStorage(jdbcTemplate);
        filmStorage.createFilm(newFilm);
        filmStorage.createFilm(newFilm2);

        // вызываем тестируемый метод
        List<Film> listFilms = filmStorage.listFilms();

        // проверяем утверждения
        assertThat(listFilms.size())
                .isEqualTo(2);
    }

    @Test
    public void testCreateFilm() {
        // Подготавливаем данные для теста
        Film newFilm = new Film(1L,
                "Фильм",
                "Описание",
                LocalDate.of(2000, 12, 12),
                26,
                new Mpa(1, "G"),
                new HashSet<>());
        FilmDbStorage filmStorage = new FilmDbStorage(jdbcTemplate);

        // вызываем тестируемый метод
        Film savedFilm = filmStorage.createFilm(newFilm);

        assertThat(savedFilm)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(newFilm);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testUpdateFilm() {
        // Подготавливаем данные для теста
        Film newFilm = new Film(1L,
                "Фильм",
                "Описание",
                LocalDate.of(2000, 12, 12),
                26,
                new Mpa(1, "G"),
                new HashSet<>());
        FilmDbStorage filmStorage = new FilmDbStorage(jdbcTemplate);
        filmStorage.createFilm(newFilm);

        Film newFilm2 = new Film(1L,
                "Фильм1111",
                "Описание222",
                LocalDate.of(2000, 12, 12),
                226,
                new Mpa(1, "G"),
                new HashSet<>());
        // вызываем тестируемый метод
        Film updateddFilm = filmStorage.updateFilm(newFilm2);

        assertThat(updateddFilm)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(newFilm2);        // и сохраненного пользователя - совпадают

    }
}