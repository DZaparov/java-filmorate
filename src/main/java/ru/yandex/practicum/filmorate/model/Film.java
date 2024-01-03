package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.yandex.practicum.filmorate.validator.FilmDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
@AllArgsConstructor
@ToString
public class Film{
    @EqualsAndHashCode.Exclude
    private Integer id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @FilmDate
    private LocalDate releaseDate;
    @Positive
    private Integer duration;

}
