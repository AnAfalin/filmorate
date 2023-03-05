package ru.lazarenko.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.lazarenko.filmorate.exception.ValidationException;
import ru.lazarenko.filmorate.model.Film;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {

    private FilmController filmController;
    private Film film;
    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeEach
    void init(){
        film = new Film(null, "Title film", "Description film", LocalDate.now(), 60);
        filmController = new FilmController();
    }

    @Test
    void createFilm_rejectName_nameIsEmpty(){
        film.setName("");
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    void createFilm_rejectName_nameIsNull(){
        film.setName(null);
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    void createFilm_rejectName_nameIsNotEmptyAndNotNull(){
        assertTrue(validator.validate(film).isEmpty());
    }

    @Test
    void createFilm_rejectDescription_descriptionIsNotEmptyAndNotNull(){
        assertTrue(validator.validate(film).isEmpty());
    }

    @Test
    void createFilm_rejectDescription_descriptionIsNull(){
        film.setDescription(null);
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    void createFilm_rejectDescription_descriptionIsEmpty(){
        film.setDescription("");
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    void createFilm_rejectReleaseDate_releaseDateIsIncorrect(){
        film.setReleaseDate(LocalDate.of(1000, 1, 1));
        assertEquals(1, validator.validate(film).size());
    }

    @Test
    void createFilm_rejectReleaseDate_releaseDateIsCorrect(){
        assertTrue(validator.validate(film).isEmpty());
    }

    @Test
    void createFilm_rejectDuration_durationIsCorrect(){
        assertTrue(validator.validate(film).isEmpty());
    }

    @Test
    void addFilm_validationException_filmAlreadyExist(){
        filmController.addFilm(film);

        Film newFilm = new Film(null, "Title film", "Description film", LocalDate.now(), 60);

        assertThrows(ValidationException.class, () -> filmController.addFilm(newFilm));
    }

    @Test
    void updateFilm_validationException_filmIdIsNull(){
        filmController.addFilm(film);

        Film newFilm = new Film(null, "Title film", "Description film", LocalDate.now(), 60);

        assertThrows(ValidationException.class, () -> filmController.updateFilm(newFilm));
    }

    @Test
    void updateFilm_validationException_filmIdIsIncorrect(){
        filmController.addFilm(film);

        Film newFilm = new Film(-5, "Title film", "Description film", LocalDate.now(), 60);

        assertThrows(ValidationException.class, () -> filmController.updateFilm(newFilm));
    }

    @Test
    void updateFilm_validationException_filmIdIsNotExist(){
        filmController.addFilm(film);

        Film newFilm = new Film(10, "Title film", "Description film", LocalDate.now(), 60);

        assertThrows(ValidationException.class, () -> filmController.updateFilm(newFilm));
    }
}
