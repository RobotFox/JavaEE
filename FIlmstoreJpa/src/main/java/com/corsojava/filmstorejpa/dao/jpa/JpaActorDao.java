package com.corsojava.filmstorejpa.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.corsojava.filmstorejpa.dao.ActorDao;
import com.corsojava.filmstorejpa.model.Actor;

@Repository("JpaActorDao")
@Transactional(propagation = Propagation.REQUIRED)
public class JpaActorDao implements ActorDao {

	@PersistenceContext
	private EntityManagerFactory emf;

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public List<Actor> listActors() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("from Actor a");
		return query.getResultList();
	}
}
