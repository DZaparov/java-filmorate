package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> listUsers();

    User createUser(User user);

    User getUserById(Long id);

    User updateUser(User user);

}
