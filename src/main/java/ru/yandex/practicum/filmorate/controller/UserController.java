package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> listUsers() {
        List<User> result = userService.listUsers();
        log.info("Получен список пользователей. Количество: " + result.size());
        return result;
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        User result = userService.getUserById(id);
        log.info("Получен пользователь: " + result);
        return result;
    }

    @PostMapping()
    public User createUser(@Valid @RequestBody User user) {
        User result = userService.createUser(user);
        log.info("Создан пользователь: " + result);
        return result;
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        User result = userService.updateUser(user);
        log.info("Обновлен пользователь: " + result);
        return result;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
        log.info("Пользователь с id=" + id + " добавил в друзья пользователя с id=" + friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.removeFriend(id, friendId);
        log.info("Пользователь с id=" + id + " удалил из друзей пользователя с id=" + friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> listFriends(@PathVariable Long id) {
        List<User> result = userService.listFriends(id);
        log.info("Получен список друзей. Количество: " + result.size());
        return result;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> listCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        List<User> result = userService.listCommonFriends(id, otherId);
        log.info("Получен список общих друзей id=" + id + " и id=" + otherId + ". Количество: " + result.size());
        return result;
    }

}
