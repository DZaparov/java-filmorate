package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikesStorage {
    void addLike(Long filmId, Long userId);

    void removeLike(Long filmId, Long userId);

    List<Film> listPopularFilms(int count);
}
