package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTests {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validateThrowsIfFilmWithEmptyNameTest() {
        try {
            Film film = new Film(null, "", "Описание", LocalDate.of(2000, 12, 12), 26);
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            assertFalse(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateDoesNotThrowIfFilmWithNoEmptyNameTest() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(2000, 12, 12), 26);
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            assertTrue(violations.isEmpty());
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
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            assertFalse(violations.isEmpty());
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
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            assertTrue(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateThrowsIfFilmWithDateBefore28dec1895Test() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 27), 26);
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            assertFalse(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateDoesNotThrowIfFilmWithDate28dec1895Test() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), 26);
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            assertTrue(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateThrowsIfFilmDurationIsZeroOrMinusTest() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), 0);
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            assertFalse(violations.isEmpty());
        } catch (Exception ignored) {
        }
        try {
            Film film2 = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), -1);
            Set<ConstraintViolation<Film>> violations = validator.validate(film2);
            assertFalse(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateDoesNotThrowIfFilmDurationIsPlusTest() {
        try {
            Film film = new Film(null, "Название", "Описание", LocalDate.of(1895, 12, 28), 26);
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            assertTrue(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }
}
