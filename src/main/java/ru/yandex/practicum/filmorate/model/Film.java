package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validator.FilmDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

/**
 * Film.
 */
@Data
@RequiredArgsConstructor
@ToString
@Builder
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
    private Mpa mpa;
    private Set<Genre> genres = new HashSet<>();

    public Film(Long id, String name, String description, LocalDate releaseDate, Integer duration, Mpa mpa, Set<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres = genres;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("releaseDate", releaseDate);
        values.put("duration", duration);
        values.put("mpa_id", mpa.getId());
        return values;
    }
}
