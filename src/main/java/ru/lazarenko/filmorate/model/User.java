package ru.lazarenko.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Null
    private Integer id;

    @NotBlank(message = "Email can not be empty or null")
    @Email
    private String email;

    @NotBlank(message = "Login can not be empty or null")
    private String login;

    private String name;

    @PastOrPresent(message = "Birthday can not ne in the future")
    private LocalDate birthday;

}
