package com.corsojava.sakilajpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.corsojava.sakilajpa.model.Language;

@Repository
public class JpaLanguageDao implements LanguageDao {

	private EntityManagerFactory emf;

	public EntityManagerFactory getEmf() {
		return emf;
	}

	@PersistenceUnit
	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<Language> getAllLanguage() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("from Language l");
		return query.getResultList();
	}

}
