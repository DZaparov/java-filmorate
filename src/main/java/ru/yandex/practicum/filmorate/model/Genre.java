package ru.yandex.practicum.filmorate.model;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@Builder
@EqualsAndHashCode
public class Genre {
    private Integer id;
    @EqualsAndHashCode.Exclude
    private String name;
}
