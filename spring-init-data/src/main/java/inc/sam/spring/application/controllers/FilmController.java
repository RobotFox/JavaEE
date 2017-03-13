package inc.sam.spring.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inc.sam.spring.application.business.services.FilmService;
import inc.sam.spring.application.model.Film;

@RestController
public class FilmController {

	@Autowired
	private FilmService filmService;

	@RequestMapping("/films")
	public List<Film> movies() {
		return filmService.getAllFilms();
	}

}
