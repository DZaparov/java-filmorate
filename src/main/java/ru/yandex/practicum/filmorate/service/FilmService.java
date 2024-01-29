package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private Long id = 0L;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public List<Film> listFilms() {
        return filmStorage.listFilms();
    }

    public Film createFilm(Film film) {
        generateFilmId(film);
        return filmStorage.createFilm(film);
    }

    public Film getFilmById(Long id) {
        return filmStorage.getFilmById(id);
    }

    public Film updateFilm(Film film) {
        generateFilmId(filmStorage.getFilmById(film.getId()));
        return filmStorage.updateFilm(film);
    }

    public Film addLike(Long filmId, Long userId) {
        Film film = getFilmById(filmId);
        film.getLikes().add(userId);
        return film;
    }

    public Film removeLike(Long filmId, Long userId) {
        Film film = getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        film.getLikes().remove(user.getId());
        return film;
    }

    public List<Film> listPopularFilms(int count) {

        List<Film> result = listFilms()
                //создаём стрим на основе списка
                .stream()
                //сортируем
                //.sorted(Comparator.comparingInt(Film::getLikesCount))
                .sorted(Collections.reverseOrder(Comparator.comparingInt(film -> film.getLikes().size())))
                //задаем количество
                .limit(count)
                //преобразуем стрим обратно в список
                .collect(Collectors.toList());

        return result;
    }

    public void generateFilmId(Film film) {
        if (film.getId() == null) {
            film.setId(++id);
        }
    }
}
