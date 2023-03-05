package ru.lazarenko.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.lazarenko.filmorate.exception.ValidationException;
import ru.lazarenko.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private int idFilm = 1;
    private final Map<Integer, Film> films = new HashMap<>();


    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Request add new Film");

        if (!films.values().stream().noneMatch(filmSaved -> filmSaved.getName().equals(film.getName()) &&
                filmSaved.getDescription().equals(film.getDescription()) &&
                filmSaved.getDuration() == film.getDuration() &&
                filmSaved.getReleaseDate().equals(film.getReleaseDate()))) {
            log.error("Film with name='%s' description='%s', date of Release='%s', duration='%s' already exist"
                    .formatted(film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration()));
            throw new ValidationException("Film %s already exist".formatted(film));
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
            throw new ValidationException("Invalid film id='%s' of updatable user".formatted(film.getId()));
        }

        if(films.get(film.getId()) == null){
            log.error("Film with id='%s' is not exist".formatted(film.getId()));
            throw new ValidationException("Invalid id='%s' of updatable user".formatted(film.getId()));
        }

        films.put(film.getId(), film);
        log.info("Updatable film {}", film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Returned get all films");
        return new ArrayList<>(films.values());
    }
}