package com.corsojava.cli;

import java.util.Iterator;
import java.util.List;

import com.corsojava.dao.DaoFactory;
import com.corsojava.dao.DaoFactoryInterface;
import com.corsojava.dao.FilmDao;
import com.corsojava.dao.SessionManager;
import com.corsojava.model.Film;

public class AppFilm {

	public static void main(String[] args) {
		DaoFactoryInterface daoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC_DAO_FACTORY_TYPE);
		SessionManager sessionManager = daoFactory.getSessionManager();
		try {
			sessionManager.startConnection();
			sessionManager.startTransaction();
			FilmDao filmDao = daoFactory.getFilmDao();
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
		}

		sessionManager.releaseConnection();
	}

}
