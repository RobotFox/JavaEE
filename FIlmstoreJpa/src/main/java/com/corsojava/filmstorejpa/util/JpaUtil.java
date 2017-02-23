package com.corsojava.filmstorejpa.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	private static EntityManagerFactory entityManagerFactory;

	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("com.corsojava_FilmstoreJpa");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static EntityManagerFactory getEntityManager() {
		return entityManagerFactory;

	}

}
