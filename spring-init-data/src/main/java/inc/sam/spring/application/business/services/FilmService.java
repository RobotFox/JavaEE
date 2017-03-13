package inc.sam.spring.application.business.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.sam.spring.application.model.Film;
import inc.sam.spring.application.repositories.FilmRepository;

@Service
public class FilmService {

	@Autowired
	private FilmRepository filmRepository;

	public List<Film> getAllFilms() {
		List<Film> films = new LinkedList<>();
		filmRepository.findAll().forEach(films::add);
		return films;
	}

	public Film getFilm(Short id) {
		// return topics.stream().filter(t ->
		// t.getId().equals(id)).findFirst().get();
		return filmRepository.findOne(id);
	}

	public void addFilm(Film film) {
		filmRepository.save(film);
	}

	public void updateFilm(Short id, Film film) {
		filmRepository.save(film);
	}

	public void deleteFilm(Short id) {
		// topics.removeIf(t -> t.getId().equals(id));
		filmRepository.delete(id);
	}

}
