package com.corsojava.SpringMVCExample.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.stream.ImageOutputStreamImpl;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveFilm(@Validated @ModelAttribute("film") Film film, BindingResult result) {
		// System.out.println(film.getTitle() + film.getLanguageId());
		// if (film.getTitle() == null || "".equals(film.getTitle())) {
		// result.rejectValue("title", "notEmpty", "Il titolo è obbligatorio");
		// }
		if (result.hasErrors()) {
			return "film/form";
		}
		filmDao.addFilm(film);
		return "redirect:list";
	}

	@RequestMapping("/download")
	public String download(HttpServletResponse res) throws IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("obi.jpg"); // esempio
																										// per
																										// ottenere
																										// un'immagine
		res.setContentType(MediaType.IMAGE_JPEG_VALUE);
		// res.setContentLength(10 * 1024 * 1024);
		res.setHeader("content-disposition", "attachment; filename\"my.pdf\"");
		ServletOutputStream os = res.getOutputStream();
		int howMany = IOUtils.copy(is, os);
		os.flush();
		os.close();
		return "redirect:list";
	}

	@ModelAttribute(name = "languages")
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
