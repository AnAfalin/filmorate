package ru.lazarenko.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lazarenko.filmorate.constraint.DateRelease;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    @Null
    private Integer id;

    @NotBlank(message = "Title of film must be not empty")
    private String name;

    @Size(min = 10, max = 200, message = "Length of film description: max = 200 and min = 10")
    @NotNull(message = "Description of film must be not null")
    private String description;

    @DateRelease(day = 28, month = 12, year = 1895, message = "Date of release must be after 28 December 1895")
    private LocalDate releaseDate;

    @Positive(message = "Duration of film must be positive value")
    private Integer duration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(name, film.name)
                && Objects.equals(description, film.description)
                && Objects.equals(releaseDate, film.releaseDate)
                && Objects.equals(duration, film.duration);
    }
}
