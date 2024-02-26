package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest // указываем, о необходимости подготовить бины для работы с БД
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserDbStorageTest {
    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testGetUserById() {
        // Подготавливаем данные для теста
        User newUser = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        UserDbStorage userStorage = new UserDbStorage(jdbcTemplate);
        userStorage.createUser(newUser);

        // вызываем тестируемый метод
        User savedUser = userStorage.getUserById(1L);

        // проверяем утверждения
        assertThat(savedUser)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(newUser);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testListUsers() {
        // Подготавливаем данные для теста
        User newUser = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        User newUser2 = new User(2L, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        UserDbStorage userStorage = new UserDbStorage(jdbcTemplate);
        userStorage.createUser(newUser);
        userStorage.createUser(newUser2);

        // вызываем тестируемый метод
        List<User> listUsers = userStorage.listUsers();

        // проверяем утверждения
        assertThat(listUsers.size())
                .isEqualTo(2);
    }

    @Test
    public void testCreateUser() {
        // Подготавливаем данные для теста
        User newUser = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        UserDbStorage userStorage = new UserDbStorage(jdbcTemplate);

        // вызываем тестируемый метод
        User savedUser = userStorage.createUser(newUser);

        assertThat(savedUser)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(newUser);        // и сохраненного пользователя - совпадают

    }

    @Test
    public void testUpdateUser() {
        // Подготавливаем данные для теста
        User newUser = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        UserDbStorage userStorage = new UserDbStorage(jdbcTemplate);
        userStorage.createUser(newUser);

        User newUser2 = new User(1L, "user2@email.ru", "vanya2", "Petr Ivanov", LocalDate.of(1992, 1, 1));
        // вызываем тестируемый метод
        User updateddUser = userStorage.updateUser(newUser2);

        assertThat(updateddUser)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(newUser2);        // и сохраненного пользователя - совпадают

    }
}