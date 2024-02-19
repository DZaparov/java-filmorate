package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTests {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validateThrowsIfUserWithEmptyEmailTest() {
        try {
            User user = new User(null, "", "login123", "Имя", LocalDate.of(2000, 12, 12));
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertFalse(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateThrowsIfUserWithoutAtInEmailTest() {
        try {
            User user = new User(null, "qwerty.ru", "login123", "Имя", LocalDate.of(2000, 12, 12));
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertFalse(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateDoesNotThrowIfUserWithGoodEmailTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "login123", "Имя", LocalDate.of(2000, 12, 12));
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertTrue(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateThrowsIfUserWithEmptyLoginTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "", "Имя", LocalDate.of(2000, 12, 12));
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertFalse(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateThrowsIfUserWithSpaceInLoginTest() {
        try {
            User user = new User(null, "user@qwerty.ru", " login123", "Имя", LocalDate.of(2000, 12, 12));
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertFalse(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateDoesNotThrowIfUserWithGoodLoginTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "login123", "Имя", LocalDate.of(2000, 12, 12));
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertTrue(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateIfUserNameIsEmptyTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "login123", "", LocalDate.of(2000, 12, 12));
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertTrue(violations.isEmpty());
            assertEquals(user.getLogin(), user.getName(), "При пустом имени должен использоваться логин");
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateThrowsIfUserBirthdayInFutureTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "login123", "Имя", LocalDate.now().plusDays(1));
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertFalse(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateDoesNotThrowIfUserBirthdayInPastTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "login123", "Имя", LocalDate.now().minusDays(1));
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertTrue(violations.isEmpty());
        } catch (Exception ignored) {
        }
    }
}
