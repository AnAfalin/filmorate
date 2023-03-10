package ru.lazarenko.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.comparator.ComparableComparator;
import org.springframework.web.bind.annotation.*;
import ru.lazarenko.filmorate.exception.ValidationException;
import ru.lazarenko.filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private int idFilm = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Request add new Film");

        if (films.values()
                .stream()
                .anyMatch(filmSaved -> (filmSaved.equals(film)))){
            log.error("Film already exist");
            throw new ValidationException("Film already exists");
        }

        film.setId(idFilm++);
        films.put(film.getId(), film);

        log.info("Successful added new Film {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Request update film");

        if (film.getId() == null || film.getId() <= 0) {
            log.error("Id updatable user must not be null or less than 1");
            throw new ValidationException("Invalid film id='" + film.getId() + "' of updatable user");
        }

        if (!films.containsKey(film.getId())) {
            log.error("Film with id='" + film.getId() + "' is not exist");
            throw new ValidationException("Invalid id='" + film.getId() + "' of updatable user");
        }

        films.put(film.getId(), film);
        log.info("Updatable film {}", film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Request get all films");
        return new ArrayList<>(films.values());
    }
}