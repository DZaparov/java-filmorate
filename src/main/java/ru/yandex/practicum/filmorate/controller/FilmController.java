package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    public final FilmService filmService;
    public final UserService userService;

    public FilmController(FilmService filmService, UserService userService) {
        this.filmService = filmService;
        this.userService = userService;
    }

    @GetMapping()
    public List<Film> listFilms() {
        List<Film> result = filmService.listFilms();
        log.info("Получен список фильмов. Количество: " + result.size());
        return result;
    }

    @GetMapping("/{id}")
    public Film findById(@PathVariable Long id) {
        Film result = filmService.getFilmById(id);
        log.info("Получен фильм: " + result);
        return result;
    }

    @PostMapping()
    public Film createFilm(@Valid @RequestBody Film film) {
        Film result = filmService.createFilm(film);
        log.info("Создан фильм: " + result);
        return result;
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        Film result = filmService.updateFilm(film);
        log.info("Обновлен фильм: " + result);
        return result;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        userService.getUserById(userId);
        filmService.getFilmById(id);
        filmService.addLike(id, userId);
        log.info("Пользователь с id=" + userId + " поставил лайк фильму с id=: " + id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.removeLike(id, userId);
        log.info("Пользователь с id=" + userId + " удалил лайк у фильма: " + id);
    }

    @GetMapping("/popular")
    public List<Film> listPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Запрошено популярных фильмов: " + count);
        List<Film> result = filmService.listPopularFilms(count);
        log.info("Получено фильмов: " + result.size());
        return result;
    }
}
