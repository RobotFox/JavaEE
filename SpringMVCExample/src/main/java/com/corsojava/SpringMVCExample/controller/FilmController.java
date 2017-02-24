package com.corsojava.SpringMVCExample.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corsojava.sakilajpa.dao.FilmDao;
import com.corsojava.sakilajpa.dao.LanguageDao;
import com.corsojava.sakilajpa.model.Film;
import com.corsojava.sakilajpa.model.Language;

@Controller
@RequestMapping("/film")
public class FilmController {
	@Resource
	private FilmDao filmDao;

	@Resource
	private LanguageDao languageDao;

	@RequestMapping("/list")
	public String showFilmList(Model model) {
		List films = filmDao.getAllFilms();
		model.addAttribute("films", films);
		return "film/list";
	}

	@RequestMapping("/show/{filmId}")
	public String showFilm(Model model, @PathVariable(value = "filmId") int id) {
		model.addAttribute("film", filmDao.getFilm(id));
		return "film/show";
	}
	// public String showFilm(Model model, @RequestParam(value = "film_id",
	// required = true, defaultValue = "12") int id) {
	// // se il parametro si chiama id
	// // int id = Integer.parseInt(request.getParameter("id"));
	// model.addAttribute("film", id); // al posto di id filmDao.getFilm(id)
	// return "film/show";
	// }

	@RequestMapping("/new")
	public String newFilm(Model model) {
		model.addAttribute("film", new Film());
		return "film/form";

	}

	@RequestMapping("/edit/{filmId}")
	public String editFilm(Model model, @PathVariable int filmId) {
		model.addAttribute("film", filmDao.getFilm(filmId));
		return "film/form";

	}

	@ModelAttribute(name="languages")
	public List<Language> getAllLanguages() {
		return languageDao.getAllLanguage();
	}
	
	// @ModelAttribute(name="languages")
	// public List<Language> getAllLanguages(Locale locale) {
	// if (locale.equals(Locale.ITALIAN)) {
	//
	// }
	// return languageDao.getAllLanguage();
	// }

}
