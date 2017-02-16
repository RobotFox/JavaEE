package com.corsojava.dao;

public interface DaoFactoryInterface {

	SessionManager getSessionManager();

	ActorDao getActorDao();
	
	FilmDao getFilmDao();

}
