/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corsojava.sakilajpa.controller;

import com.corsojava.sakilajpa.controller.exceptions.NonexistentEntityException;
import com.corsojava.sakilajpa.controller.exceptions.PreexistingEntityException;
import com.corsojava.sakilajpa.model.FilmText;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class FilmTextJpaController implements Serializable {

    public FilmTextJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FilmText filmText) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(filmText);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFilmText(filmText.getFilmId()) != null) {
                throw new PreexistingEntityException("FilmText " + filmText + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FilmText filmText) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            filmText = em.merge(filmText);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = filmText.getFilmId();
                if (findFilmText(id) == null) {
                    throw new NonexistentEntityException("The filmText with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FilmText filmText;
            try {
                filmText = em.getReference(FilmText.class, id);
                filmText.getFilmId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The filmText with id " + id + " no longer exists.", enfe);
            }
            em.remove(filmText);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FilmText> findFilmTextEntities() {
        return findFilmTextEntities(true, -1, -1);
    }

    public List<FilmText> findFilmTextEntities(int maxResults, int firstResult) {
        return findFilmTextEntities(false, maxResults, firstResult);
    }

    private List<FilmText> findFilmTextEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FilmText.class));
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

    public FilmText findFilmText(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FilmText.class, id);
        } finally {
            em.close();
        }
    }

    public int getFilmTextCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FilmText> rt = cq.from(FilmText.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
