package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    private Integer id = 0;
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> listFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        if (validateFilm(film)) {
            films.put(film.getId(), film);
            log.info("Создан фильм: {}", film);
        }
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (validateFilm(film) && films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            log.warn("Фильм не найден с id {} не найден", film.getId());
            throw new ValidationException("Фильм не найден");
        }
        return film;
    }

    public boolean validateFilm(Film film) {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Film> violation : violations) {
                log.warn("Валидация поля {} = '{}' не пройдена: {}", violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage());
            }
            throw new ValidationException("Валидация не пройдена");
        }

        if (film.getId() == null) {
            film.setId(++id);
        }
        return true;
    }
}
