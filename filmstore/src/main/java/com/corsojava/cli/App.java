package com.corsojava.cli;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.corsojava.dao.ActorDao;
import com.corsojava.dao.ActorDao;
import com.corsojava.dao.SessionManager;
import com.corsojava.model.Actor;

public class App {

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private ActorDao actorDao;

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public ActorDao getActorDao() {
		return actorDao;
	}

	public void setActorDao(ActorDao actorDao) {
		this.actorDao = actorDao;
	}

	public void runApplication() {
		try {
			sessionManager.startConnection();
			sessionManager.startTransaction();
			List<Actor> actors = actorDao.getAllActors();
			Iterator<Actor> it = actors.iterator();
			while (it.hasNext()) {
				Actor actor = (Actor) it.next();
				System.out.println(actor.getId() + " " + actor.getFirstName() + " " + actor.getLastName());
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
		App appActor = new App();
		context.getAutowireCapableBeanFactory().autowireBeanProperties(appActor,
				AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
		appActor.runApplication();
	}

}
