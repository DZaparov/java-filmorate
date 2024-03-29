package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> listFilms();

    Film createFilm(Film film);

    Film getFilmById(Long id);

    Film updateFilm(Film film);
}
