package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validator.FilmDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

/**
 * Film.
 */
@Data
@RequiredArgsConstructor
@ToString
public class Film {
    @EqualsAndHashCode.Exclude
    private Long id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @FilmDate
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    private Set<Long> likes = new TreeSet<>();

    public Film(Long id, String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
