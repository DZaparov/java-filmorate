package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    private Integer id = 0;
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (validateUser(user)) {
            users.put(user.getId(), user);
            log.info("Создан пользователь: {}", user);
        }
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (validateUser(user) && users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            log.warn("Пользователь не найден с id {} не найден", user.getId());
            throw new ValidationException("Пользователь не найден");
        }
        return user;
    }

    public boolean validateUser(User user) {
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
        return true;
    }
}
