package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {
    private Long id = 0L;
    private final UserStorage userStorage;
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> listUsers() {
        return userStorage.listUsers();
    }

    public User createUser(User user) {
        validateUser(user);
        return userStorage.createUser(user);
    }

    public User getUserById(Long Id) {
        return userStorage.getUserById(Id);
    }

    public User updateUser(User user) {
        validateUser(userStorage.getUserById(user.getId()));
        return userStorage.updateUser(user);
    }

    public void addFriend(Long id, Long friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(id);
    }

    public void removeFriend(Long id, Long friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
    }

    public List<User> listFriends(Long id) {
        List<User> result = new ArrayList<>();

        User user = getUserById(id);
        for (Long friendId : user.getFriends()) {
            result.add(getUserById(friendId));
        }

        return result;
    }

    public List<User> listCommonFriends(Long id, Long otherId) {
        List<User> result = new ArrayList<>();

        Set<Long> friends = getUserById(id).getFriends();
        Set<Long> otherFriends = getUserById(otherId).getFriends();

        for (Long friendId : friends) {
            if (otherFriends.contains(friendId)) {
                result.add(getUserById(friendId));
            }
        }
        return result;
    }

    public void validateUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                log.warn("Валидация поля {} = '{}' не пройдена: {}", violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage());
            }
            throw new ValidationException("Валидация не пройдена");
        }

        if (user.getId() == null) {
            user.setId(++id);
        }
    }


}
