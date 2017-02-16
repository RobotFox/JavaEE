package com.corsojava.dao.jdbc;

import com.corsojava.dao.ActorDao;
import com.corsojava.dao.DaoFactoryInterface;
import com.corsojava.dao.FilmDao;
import com.corsojava.dao.LanguageDao;
import com.corsojava.dao.SessionManager;

public class JdbcDaoFactory implements DaoFactoryInterface {

	private JdbcSessionManager sessionManager;

	public JdbcDaoFactory(JdbcSessionManager sessionManager) {
		super();
		this.sessionManager = sessionManager;
	}

	public ActorDao getActorDao() {
		return new JdbcActorDao(sessionManager);
	}

	public SessionManager getSessionManager() {
		return this.sessionManager;
	}

	public FilmDao getFilmDao() {
		return new JdbcFilmDao(sessionManager);
	}

	public LanguageDao getLanguageDao() {
		return new JdbcLanguageDao(sessionManager);
	}

}
