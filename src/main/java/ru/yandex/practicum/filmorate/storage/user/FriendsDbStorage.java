package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
@Primary
@Repository
@Slf4j
public class FriendsDbStorage implements FriendsStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendsDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(Long followingUserId, Long followedUserId) {
        String sqlQuery = "INSERT INTO friends (following_user_id, followed_user_id) VALUES(?, ?)";
        int recordsAffected = jdbcTemplate.update(sqlQuery, followingUserId, followedUserId);
        if (recordsAffected == 0) {
            log.warn("Пользователь с id = {} запрашивает дружбу у id = {}.  Пользователь не найден",
                    followingUserId,
                    followedUserId);
            throw new NotFoundException("Запрос дружбы "
                    + followingUserId + " -> " + followedUserId
                    + ". Пользователь не найден");
        }
        log.info("Создана дружба: {} -> {}", followingUserId, followedUserId);
    }

    @Override
    public void removeFriend(Long userId, Long removedUserId) {
        String sqlQuery = "DELETE FROM friends WHERE following_user_id = ? AND followed_user_id = ?";
        int recordsAffected = jdbcTemplate.update(sqlQuery, userId, removedUserId);
        if (recordsAffected == 0) {
            log.warn("Пользователь с id = {} отзывает дружбу у id = {}.  Пользователь не найден",
                    userId,
                    removedUserId);
            throw new NotFoundException("Отзыв дружбы "
                    + userId + " -> " + removedUserId
                    + ". Пользователь не найден");
        }
        log.info("Удалена дружба: {} -> {}", userId, removedUserId);
    }

    @Override
    public List<User> listFriends(Long userId) {
        String sqlQuery = "SELECT * " +
                "FROM friends f " +
                "LEFT JOIN users u ON f.followed_user_id = u.id " +
                "WHERE f.following_user_id = ?";
        List<User> friendsList = jdbcTemplate.query(sqlQuery, new MapRowToUser(), userId);
        log.info("Список друзей пользователя id={}: {}", userId, friendsList);
        return friendsList;
    }

    @Override
    public List<User> listCommonFriends(Long id, Long otherId) {
        String sqlQuery = "SELECT * FROM users " +
                "WHERE id IN " +
                "(" +
                "SELECT followed_user_id FROM friends " +
                "WHERE following_user_id IN (?, ?) " +
                "GROUP BY followed_user_id " +
                "HAVING COUNT(followed_user_id) = 2" +
                ")";
        List<User> friendsList = jdbcTemplate.query(sqlQuery, new MapRowToUser(), id, otherId);
        log.info("Список общих друзей пользователей id={} и {}: {}", id, otherId, friendsList);
        return friendsList;
    }
}
