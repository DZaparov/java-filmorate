package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTests {
    private final UserController userController = new UserController();

    @Test
    void validateFalseIfUserWithEmptyEmailTest() {
        try {
            User user = new User(null, "", "login123", "Имя", LocalDate.of(2000, 12, 12));
            assertFalse(userController.validateUser(user));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateFalseIfUserWithoutAtInEmailTest() {
        try {
            User user = new User(null, "qwerty.ru", "login123", "Имя", LocalDate.of(2000, 12, 12));
            assertFalse(userController.validateUser(user));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateTrueIfUserWithGoodEmailTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "login123", "Имя", LocalDate.of(2000, 12, 12));
            assertTrue(userController.validateUser(user));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateFalseIfUserWithEmptyLoginTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "", "Имя", LocalDate.of(2000, 12, 12));
            assertFalse(userController.validateUser(user));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateFalseIfUserWithSpaceInLoginTest() {
        try {
            User user = new User(null, "user@qwerty.ru", " login123", "Имя", LocalDate.of(2000, 12, 12));
            assertFalse(userController.validateUser(user));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateTrueIfUserWithGoodLoginTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "login123", "Имя", LocalDate.of(2000, 12, 12));
            assertTrue(userController.validateUser(user));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateIfUserNameIsEmptyTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "login123", "", LocalDate.of(2000, 12, 12));
            assertTrue(userController.validateUser(user));
            assertEquals(user.getLogin(), user.getName(), "При пустом имени должен использоваться логин");
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateFalseIfUserBirthdayInFutureTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "login123", "Имя", LocalDate.now().plusDays(1));
            assertFalse(userController.validateUser(user));
        } catch (Exception ignored) {
        }
    }

    @Test
    void validateTrueIfUserBirthdayInPastTest() {
        try {
            User user = new User(null, "user@qwerty.ru", "login123", "Имя", LocalDate.now().minusDays(1));
            assertTrue(userController.validateUser(user));
        } catch (Exception ignored) {
        }
    }
}
