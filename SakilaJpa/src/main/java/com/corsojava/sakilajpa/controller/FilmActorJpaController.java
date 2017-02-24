/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corsojava.sakilajpa.controller;

import com.corsojava.sakilajpa.controller.exceptions.NonexistentEntityException;
import com.corsojava.sakilajpa.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.corsojava.sakilajpa.model.Actor;
import com.corsojava.sakilajpa.model.Film;
import com.corsojava.sakilajpa.model.FilmActor;
import com.corsojava.sakilajpa.model.FilmActorPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class FilmActorJpaController implements Serializable {

    public FilmActorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FilmActor filmActor) throws PreexistingEntityException, Exception {
        if (filmActor.getFilmActorPK() == null) {
            filmActor.setFilmActorPK(new FilmActorPK());
        }
        filmActor.getFilmActorPK().setFilmId(filmActor.getFilm().getFilmId());
        filmActor.getFilmActorPK().setActorId(filmActor.getActor().getActorId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actor actor = filmActor.getActor();
            if (actor != null) {
                actor = em.getReference(actor.getClass(), actor.getActorId());
                filmActor.setActor(actor);
            }
            Film film = filmActor.getFilm();
            if (film != null) {
                film = em.getReference(film.getClass(), film.getFilmId());
                filmActor.setFilm(film);
            }
            em.persist(filmActor);
            if (actor != null) {
                actor.getFilmActorCollection().add(filmActor);
                actor = em.merge(actor);
            }
            if (film != null) {
                film.getFilmActorCollection().add(filmActor);
                film = em.merge(film);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFilmActor(filmActor.getFilmActorPK()) != null) {
                throw new PreexistingEntityException("FilmActor " + filmActor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FilmActor filmActor) throws NonexistentEntityException, Exception {
        filmActor.getFilmActorPK().setFilmId(filmActor.getFilm().getFilmId());
        filmActor.getFilmActorPK().setActorId(filmActor.getActor().getActorId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FilmActor persistentFilmActor = em.find(FilmActor.class, filmActor.getFilmActorPK());
            Actor actorOld = persistentFilmActor.getActor();
            Actor actorNew = filmActor.getActor();
            Film filmOld = persistentFilmActor.getFilm();
            Film filmNew = filmActor.getFilm();
            if (actorNew != null) {
                actorNew = em.getReference(actorNew.getClass(), actorNew.getActorId());
                filmActor.setActor(actorNew);
            }
            if (filmNew != null) {
                filmNew = em.getReference(filmNew.getClass(), filmNew.getFilmId());
                filmActor.setFilm(filmNew);
            }
            filmActor = em.merge(filmActor);
            if (actorOld != null && !actorOld.equals(actorNew)) {
                actorOld.getFilmActorCollection().remove(filmActor);
                actorOld = em.merge(actorOld);
            }
            if (actorNew != null && !actorNew.equals(actorOld)) {
                actorNew.getFilmActorCollection().add(filmActor);
                actorNew = em.merge(actorNew);
            }
            if (filmOld != null && !filmOld.equals(filmNew)) {
                filmOld.getFilmActorCollection().remove(filmActor);
                filmOld = em.merge(filmOld);
            }
            if (filmNew != null && !filmNew.equals(filmOld)) {
                filmNew.getFilmActorCollection().add(filmActor);
                filmNew = em.merge(filmNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FilmActorPK id = filmActor.getFilmActorPK();
                if (findFilmActor(id) == null) {
                    throw new NonexistentEntityException("The filmActor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FilmActorPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FilmActor filmActor;
            try {
                filmActor = em.getReference(FilmActor.class, id);
                filmActor.getFilmActorPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The filmActor with id " + id + " no longer exists.", enfe);
            }
            Actor actor = filmActor.getActor();
            if (actor != null) {
                actor.getFilmActorCollection().remove(filmActor);
                actor = em.merge(actor);
            }
            Film film = filmActor.getFilm();
            if (film != null) {
                film.getFilmActorCollection().remove(filmActor);
                film = em.merge(film);
            }
            em.remove(filmActor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FilmActor> findFilmActorEntities() {
        return findFilmActorEntities(true, -1, -1);
    }

    public List<FilmActor> findFilmActorEntities(int maxResults, int firstResult) {
        return findFilmActorEntities(false, maxResults, firstResult);
    }

    private List<FilmActor> findFilmActorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FilmActor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FilmActor findFilmActor(FilmActorPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FilmActor.class, id);
        } finally {
            em.close();
        }
    }

    public int getFilmActorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FilmActor> rt = cq.from(FilmActor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
