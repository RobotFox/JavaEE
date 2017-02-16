package com.corsojava.cli;

import java.util.Iterator;
import java.util.List;

import com.corsojava.dao.ActorDao;
import com.corsojava.dao.DaoFactory;
import com.corsojava.dao.DaoFactoryInterface;
import com.corsojava.dao.SessionManager;
import com.corsojava.model.Actor;

public class App {

	public static void main(String[] args) {
		DaoFactoryInterface daoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC_DAO_FACTORY_TYPE);
		SessionManager sessionManager = daoFactory.getSessionManager();
		try {
			sessionManager.startConnection();
			sessionManager.startTransaction();
			ActorDao actorDao = daoFactory.getActorDao();
			List<Actor> actors = actorDao.getAllActors();
			Iterator<Actor> it = actors.iterator();
			while (it.hasNext()) {
				Actor actor = (Actor) it.next();
				System.out.println(actor.getId() + " " + actor.getFirstName() + " " + actor.getLastName());
			}
			sessionManager.commitTransaction();
		} catch (Exception e) {
			sessionManager.rollbackTransaction();
		}

		sessionManager.releaseConnection();
	}

}
