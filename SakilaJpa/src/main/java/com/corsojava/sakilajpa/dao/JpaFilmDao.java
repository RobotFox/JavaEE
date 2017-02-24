package com.corsojava.sakilajpa.dao;

import com.corsojava.sakilajpa.model.Film;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
@Repository
public class JpaFilmDao implements FilmDao {

	private EntityManagerFactory emf;

	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Film getFilm(int id) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("from Film f where f.filmId = " + id);
		return (Film) query.getSingleResult();
	}

	@Override
	public List<Film> getAllFilms() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("from Film f");
		return query.getResultList();
	}

	@Override
	public int addFilm(Film actor) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public int updateFilm(int id, Film film) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public int deleteFilm(int id) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public List<Film> findFilmByTitle(String title) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createNamedQuery("Film.findByPartialTitle", Film.class);
		query.setParameter("title", title);
		return query.getResultList();
	}

}
