package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsStorage {
    void addFriend(Long followingUserId, Long followedUserId);

    void removeFriend(Long userId, Long removedUserId);

    List<User> listFriends(Long userId);

    List<User> listCommonFriends(Long id, Long otherId);
}
