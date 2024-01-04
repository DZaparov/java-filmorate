package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FilmControllerTests {
    private final FilmController filmController = new FilmController();

    @Test
    void validateThrowsIfFilmWithEmptyNameTest() {
        try {
            Film film = new Film(null, "", "Описание", LocalDate.of(2000, 12, 12), 26);
            assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateDoesNotThrowIfFilmWithNoEmptyNameTest() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(2000, 12, 12), 26);
            assertDoesNotThrow(() -> filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateThrowsIfFilmWithDescriptionLengthMoreThan200Test() {
        try {
            Film film = new Film(null, "Название",
                    "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписание1", LocalDate.of(2000, 12, 12), 26);
            assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateDoesNotThrowIfFilmWithDescriptionLength200Test() {
        try {
            Film film = new Film(null, "Название",
                    "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписаниеОписаниеОписаниеОписание" +
                            "ОписаниеОписаниеОписаниеОписание", LocalDate.of(2000, 12, 12), 26);
            assertDoesNotThrow(() -> filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateThrowsIfFilmWithDateBefore28dec1895Test() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 27), 26);
            assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateDoesNotThrowIfFilmWithDate28dec1895Test() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), 26);
            assertDoesNotThrow(() -> filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateThrowsIfFilmDurationIsZeroOrMinusTest() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), 0);
            assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
        try {
            Film film2 = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), -1);
            assertThrows(ValidationException.class, () -> filmController.validateFilm(film2));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateDoesNotThrowIfFilmDurationIsPlusTest() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), 26);
            assertDoesNotThrow(() -> filmController.validateFilm(film));
        } catch (Exception ignored) {
        }
    }
}
