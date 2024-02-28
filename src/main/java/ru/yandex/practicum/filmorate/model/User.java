package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@ToString
@Builder
public class User {
    @EqualsAndHashCode.Exclude
    private Long id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Имя пользователя не должно содержать специальных символов")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;

    public User(Long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;

        if (this.name == null || this.name.isBlank()) {
            this.name = login;
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("login", login);
        values.put("name", name);
        values.put("birthday", birthday);
        return values;
    }
}
