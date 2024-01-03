package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmControllerTests {
    private final FilmController filmController = new FilmController();
    ;

    @Test
    void validateFalseIfFilmWithEmptyNameTest() {
        try {
            Film film = new Film(null, "", "Описание", LocalDate.of(2000, 12, 12), 26);
            assertFalse(filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateTrueIfFilmWithNoEmptyNameTest() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(2000, 12, 12), 26);
            assertTrue(filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateFalseIfFilmWithDescriptionLengthMoreThan200Test() {
        try {
            Film film = new Film(null, "Название",
                    "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписание1", LocalDate.of(2000, 12, 12), 26);
            assertFalse(filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateTrueIfFilmWithDescriptionLength200Test() {
        try {
            Film film = new Film(null, "Название",
                    "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписание", LocalDate.of(2000, 12, 12), 26);
            assertTrue(filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateFalseIfFilmWithDateBefore28dec1895Test() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 27), 26);
            assertFalse(filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateTrueIfFilmWithDate28dec1895Test() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), 26);
            assertTrue(filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateFalseIfFilmDurationIsZeroOrMinusTest() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), 0);
            assertFalse(filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
        try {
            Film film2 = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), -1);
            assertFalse(filmController.validateFilm(film2));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateTrueIfFilmDurationIsPlusTest() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), 26);
            assertTrue(filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }
}
