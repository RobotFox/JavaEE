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
import com.corsojava.sakilajpa.model.Category;
import com.corsojava.sakilajpa.model.Film;
import com.corsojava.sakilajpa.model.FilmCategory;
import com.corsojava.sakilajpa.model.FilmCategoryPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class FilmCategoryJpaController implements Serializable {

    public FilmCategoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FilmCategory filmCategory) throws PreexistingEntityException, Exception {
        if (filmCategory.getFilmCategoryPK() == null) {
            filmCategory.setFilmCategoryPK(new FilmCategoryPK());
        }
        filmCategory.getFilmCategoryPK().setCategoryId(filmCategory.getCategory().getCategoryId());
        filmCategory.getFilmCategoryPK().setFilmId(filmCategory.getFilm().getFilmId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category category = filmCategory.getCategory();
            if (category != null) {
                category = em.getReference(category.getClass(), category.getCategoryId());
                filmCategory.setCategory(category);
            }
            Film film = filmCategory.getFilm();
            if (film != null) {
                film = em.getReference(film.getClass(), film.getFilmId());
                filmCategory.setFilm(film);
            }
            em.persist(filmCategory);
            if (category != null) {
                category.getFilmCategoryCollection().add(filmCategory);
                category = em.merge(category);
            }
            if (film != null) {
                film.getFilmCategoryCollection().add(filmCategory);
                film = em.merge(film);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFilmCategory(filmCategory.getFilmCategoryPK()) != null) {
                throw new PreexistingEntityException("FilmCategory " + filmCategory + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FilmCategory filmCategory) throws NonexistentEntityException, Exception {
        filmCategory.getFilmCategoryPK().setCategoryId(filmCategory.getCategory().getCategoryId());
        filmCategory.getFilmCategoryPK().setFilmId(filmCategory.getFilm().getFilmId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FilmCategory persistentFilmCategory = em.find(FilmCategory.class, filmCategory.getFilmCategoryPK());
            Category categoryOld = persistentFilmCategory.getCategory();
            Category categoryNew = filmCategory.getCategory();
            Film filmOld = persistentFilmCategory.getFilm();
            Film filmNew = filmCategory.getFilm();
            if (categoryNew != null) {
                categoryNew = em.getReference(categoryNew.getClass(), categoryNew.getCategoryId());
                filmCategory.setCategory(categoryNew);
            }
            if (filmNew != null) {
                filmNew = em.getReference(filmNew.getClass(), filmNew.getFilmId());
                filmCategory.setFilm(filmNew);
            }
            filmCategory = em.merge(filmCategory);
            if (categoryOld != null && !categoryOld.equals(categoryNew)) {
                categoryOld.getFilmCategoryCollection().remove(filmCategory);
                categoryOld = em.merge(categoryOld);
            }
            if (categoryNew != null && !categoryNew.equals(categoryOld)) {
                categoryNew.getFilmCategoryCollection().add(filmCategory);
                categoryNew = em.merge(categoryNew);
            }
            if (filmOld != null && !filmOld.equals(filmNew)) {
                filmOld.getFilmCategoryCollection().remove(filmCategory);
                filmOld = em.merge(filmOld);
            }
            if (filmNew != null && !filmNew.equals(filmOld)) {
                filmNew.getFilmCategoryCollection().add(filmCategory);
                filmNew = em.merge(filmNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FilmCategoryPK id = filmCategory.getFilmCategoryPK();
                if (findFilmCategory(id) == null) {
                    throw new NonexistentEntityException("The filmCategory with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FilmCategoryPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FilmCategory filmCategory;
            try {
                filmCategory = em.getReference(FilmCategory.class, id);
                filmCategory.getFilmCategoryPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The filmCategory with id " + id + " no longer exists.", enfe);
            }
            Category category = filmCategory.getCategory();
            if (category != null) {
                category.getFilmCategoryCollection().remove(filmCategory);
                category = em.merge(category);
            }
            Film film = filmCategory.getFilm();
            if (film != null) {
                film.getFilmCategoryCollection().remove(filmCategory);
                film = em.merge(film);
            }
            em.remove(filmCategory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FilmCategory> findFilmCategoryEntities() {
        return findFilmCategoryEntities(true, -1, -1);
    }

    public List<FilmCategory> findFilmCategoryEntities(int maxResults, int firstResult) {
        return findFilmCategoryEntities(false, maxResults, firstResult);
    }

    private List<FilmCategory> findFilmCategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FilmCategory.class));
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

    public FilmCategory findFilmCategory(FilmCategoryPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FilmCategory.class, id);
        } finally {
            em.close();
        }
    }

    public int getFilmCategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FilmCategory> rt = cq.from(FilmCategory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
