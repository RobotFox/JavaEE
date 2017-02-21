package com.corsojava.cli;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.corsojava.dao.FilmDao;
import com.corsojava.dao.SessionManager;
import com.corsojava.model.Film;

public class AppFilm {

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private FilmDao filmDao;

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public FilmDao getFilmDao() {
		return filmDao;
	}

	public void setFilmDao(FilmDao filmDao) {
		this.filmDao = filmDao;
	}

	public void runApplication() {
		try {
			sessionManager.startConnection();
			sessionManager.startTransaction();
			List<Film> films = filmDao.getAllFilms();
			Iterator<Film> it = films.iterator();
			while (it.hasNext()) {
				Film film = (Film) it.next();
				System.out.println(film.getFilm_id() + " " + film.getTitle() + " " + film.getDescription() + " "
						+ film.getLength() + " " + film.getRelease_year() + " " + film.getOriginal_language_id() + " "
						+ film.getLanguage_id() + " " + film.getActors());
			}
			sessionManager.commitTransaction();
		} catch (Exception e) {
			sessionManager.rollbackTransaction();
		} finally {
			sessionManager.releaseConnection();
		}
	}

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "dao.xml" });
		((AbstractApplicationContext) context).registerShutdownHook();
		AppFilm appFilm = new AppFilm();
		context.getAutowireCapableBeanFactory().autowireBeanProperties(appFilm,
				AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
		appFilm.runApplication();
	}
}
