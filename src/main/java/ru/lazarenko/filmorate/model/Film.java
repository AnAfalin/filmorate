package ru.lazarenko.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lazarenko.filmorate.constraint.DateRelease;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    @Null
    private Integer id;

    @NotBlank(message = "Title of film can not be empty")
    private String name;

    @Size(min = 10, max = 200,
            message = "Max length of film description is 200 characters and min length is 10 characters")
    @NotNull
    private String description;

    @DateRelease(day = 28, month = 12, year = 1895,
            message = "Date of release not be before than 28 December 1895")
    private LocalDate releaseDate;

    @Positive(message = "Duration of film must be positive value")
    private int duration;
}
