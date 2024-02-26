package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    public List<User> listUsers() {
        return userStorage.listUsers();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void addFriend(Long id, Long friendId) {
        friendsStorage.addFriend(id, friendId);
    }

    public void removeFriend(Long id, Long friendId) {
        friendsStorage.removeFriend(id, friendId);
    }

    public List<User> listFriends(Long id) {
        return friendsStorage.listFriends(id);
    }

    public List<User> listCommonFriends(Long id, Long otherId) {
        return friendsStorage.listCommonFriends(id, otherId);
    }

}
