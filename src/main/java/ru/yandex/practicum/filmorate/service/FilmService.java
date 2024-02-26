package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.GenreStorage;
import ru.yandex.practicum.filmorate.storage.film.LikesStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;
    private final LikesStorage likesStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage, GenreStorage genreStorage, LikesStorage likesStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreStorage = genreStorage;
        this.likesStorage = likesStorage;
    }

    public List<Film> listFilms() {
        List<Film> films = filmStorage.listFilms();
        return genreStorage.getListGenresForListFilms(films);
    }

    public Film createFilm(Film film) {
        filmStorage.createFilm(film);
        genreStorage.addGenres(film);
        return film;
    }

    public Film getFilmById(Long id) {
        Film film = filmStorage.getFilmById(id);
        film.setGenres(new HashSet<>(genreStorage.getFilmGenres(film)));
        return film;
    }

    public Film updateFilm(Film film) {
        filmStorage.updateFilm(film);
        genreStorage.updateGenres(film);
        return film;
    }

    public void addLike(Long filmId, Long userId) {
        likesStorage.addLike(filmId, userId);
    }

    public void removeLike(Long filmId, Long userId) {
        likesStorage.removeLike(filmId, userId);
    }

    public List<Film> listPopularFilms(int count) {
        return likesStorage.listPopularFilms(count);
    }
}
