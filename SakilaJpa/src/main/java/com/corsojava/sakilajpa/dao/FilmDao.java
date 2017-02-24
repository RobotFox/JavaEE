package com.corsojava.sakilajpa.dao;

import com.corsojava.sakilajpa.model.Film;
import java.util.List;


public interface FilmDao {
	Film getFilm(int id);
	List<Film> getAllFilms();
    int addFilm(Film actor);
    int updateFilm(int id, Film film);
    int deleteFilm(int id);
    List<Film> findFilmByTitle(String title);
}
