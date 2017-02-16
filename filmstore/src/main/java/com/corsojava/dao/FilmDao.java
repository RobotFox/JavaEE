package com.corsojava.dao;

import java.util.List;

import com.corsojava.model.Actor;
import com.corsojava.model.Film;

public interface FilmDao {

	Film getFilm(int film_id);

	int addFilm(Film film);

	int updateFilm(int film_id, Film film);

	int deleteFilm(int film_id);

	List<Film> getAllFilms();

	List<Film> findFilms(Film film);

	List<Actor> actorsInFilm(int film_id);

}
