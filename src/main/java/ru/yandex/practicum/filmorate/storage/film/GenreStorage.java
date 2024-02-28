package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    void addGenres(Film film);

    void updateGenres(Film film);

    List<Genre> getAllGenres();

    List<Genre> getFilmGenres(Film film);

    Genre getGenreById(Integer id);

    List<Film> getListGenresForListFilms(List<Film> listFilms);
}
