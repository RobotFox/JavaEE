package com.corsojava.sakiladata.cli;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.corsojava.sakiladata.model.Actor;
import com.corsojava.sakiladata.model.Film;
import com.corsojava.sakiladata.util.HibernateUtil;

public class ListActors {

	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Actor");
		List<Actor> resultList = query.getResultList();
		for (Actor actor : resultList) {
			System.out.println(actor.getFirstName() + " " + actor.getLastName());
		}
		Query query2 = session.createQuery("from Film f where f.film_id = 1");
		List<Film> resultList2 = query2.getResultList();
		for (Film film : resultList2) {
			System.out.println(film.getTitle() + " " + film.getLanguage_id().getName() + " " + film.getDescription());

		}
		tx.commit();
		session.close();
		sessionFactory.close();

	}

}
