package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    public final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
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
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        Film result = filmService.addLike(id, userId);
        log.info("Пользователь с id=" + userId + " поставил лайк фильму: " + result);
        return result;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable Long id, @PathVariable Long userId) {
        Film result = filmService.removeLike(id, userId);
        log.info("Пользователь с id=" + userId + " удалил лайк у фильма: " + result);
        return result;
    }

    @GetMapping("/popular")
    public List<Film> listPopularFilms(@RequestParam(defaultValue = "10") int count) {
        List<Film> result = filmService.listPopularFilms(count);
        log.info("Запрошено популярных фильмов: " + count + " Получено фильмов: " + result.size());
        return result;
    }
}
