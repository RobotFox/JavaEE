/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corsojava.sakilajpa.controller;

import com.corsojava.sakilajpa.controller.exceptions.IllegalOrphanException;
import com.corsojava.sakilajpa.controller.exceptions.NonexistentEntityException;
import com.corsojava.sakilajpa.model.Category;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.corsojava.sakilajpa.model.FilmCategory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class CategoryJpaController implements Serializable {

    public CategoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Category category) {
        if (category.getFilmCategoryCollection() == null) {
            category.setFilmCategoryCollection(new ArrayList<FilmCategory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<FilmCategory> attachedFilmCategoryCollection = new ArrayList<FilmCategory>();
            for (FilmCategory filmCategoryCollectionFilmCategoryToAttach : category.getFilmCategoryCollection()) {
                filmCategoryCollectionFilmCategoryToAttach = em.getReference(filmCategoryCollectionFilmCategoryToAttach.getClass(), filmCategoryCollectionFilmCategoryToAttach.getFilmCategoryPK());
                attachedFilmCategoryCollection.add(filmCategoryCollectionFilmCategoryToAttach);
            }
            category.setFilmCategoryCollection(attachedFilmCategoryCollection);
            em.persist(category);
            for (FilmCategory filmCategoryCollectionFilmCategory : category.getFilmCategoryCollection()) {
                Category oldCategoryOfFilmCategoryCollectionFilmCategory = filmCategoryCollectionFilmCategory.getCategory();
                filmCategoryCollectionFilmCategory.setCategory(category);
                filmCategoryCollectionFilmCategory = em.merge(filmCategoryCollectionFilmCategory);
                if (oldCategoryOfFilmCategoryCollectionFilmCategory != null) {
                    oldCategoryOfFilmCategoryCollectionFilmCategory.getFilmCategoryCollection().remove(filmCategoryCollectionFilmCategory);
                    oldCategoryOfFilmCategoryCollectionFilmCategory = em.merge(oldCategoryOfFilmCategoryCollectionFilmCategory);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Category category) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category persistentCategory = em.find(Category.class, category.getCategoryId());
            Collection<FilmCategory> filmCategoryCollectionOld = persistentCategory.getFilmCategoryCollection();
            Collection<FilmCategory> filmCategoryCollectionNew = category.getFilmCategoryCollection();
            List<String> illegalOrphanMessages = null;
            for (FilmCategory filmCategoryCollectionOldFilmCategory : filmCategoryCollectionOld) {
                if (!filmCategoryCollectionNew.contains(filmCategoryCollectionOldFilmCategory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FilmCategory " + filmCategoryCollectionOldFilmCategory + " since its category field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<FilmCategory> attachedFilmCategoryCollectionNew = new ArrayList<FilmCategory>();
            for (FilmCategory filmCategoryCollectionNewFilmCategoryToAttach : filmCategoryCollectionNew) {
                filmCategoryCollectionNewFilmCategoryToAttach = em.getReference(filmCategoryCollectionNewFilmCategoryToAttach.getClass(), filmCategoryCollectionNewFilmCategoryToAttach.getFilmCategoryPK());
                attachedFilmCategoryCollectionNew.add(filmCategoryCollectionNewFilmCategoryToAttach);
            }
            filmCategoryCollectionNew = attachedFilmCategoryCollectionNew;
            category.setFilmCategoryCollection(filmCategoryCollectionNew);
            category = em.merge(category);
            for (FilmCategory filmCategoryCollectionNewFilmCategory : filmCategoryCollectionNew) {
                if (!filmCategoryCollectionOld.contains(filmCategoryCollectionNewFilmCategory)) {
                    Category oldCategoryOfFilmCategoryCollectionNewFilmCategory = filmCategoryCollectionNewFilmCategory.getCategory();
                    filmCategoryCollectionNewFilmCategory.setCategory(category);
                    filmCategoryCollectionNewFilmCategory = em.merge(filmCategoryCollectionNewFilmCategory);
                    if (oldCategoryOfFilmCategoryCollectionNewFilmCategory != null && !oldCategoryOfFilmCategoryCollectionNewFilmCategory.equals(category)) {
                        oldCategoryOfFilmCategoryCollectionNewFilmCategory.getFilmCategoryCollection().remove(filmCategoryCollectionNewFilmCategory);
                        oldCategoryOfFilmCategoryCollectionNewFilmCategory = em.merge(oldCategoryOfFilmCategoryCollectionNewFilmCategory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = category.getCategoryId();
                if (findCategory(id) == null) {
                    throw new NonexistentEntityException("The category with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category category;
            try {
                category = em.getReference(Category.class, id);
                category.getCategoryId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The category with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<FilmCategory> filmCategoryCollectionOrphanCheck = category.getFilmCategoryCollection();
            for (FilmCategory filmCategoryCollectionOrphanCheckFilmCategory : filmCategoryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Category (" + category + ") cannot be destroyed since the FilmCategory " + filmCategoryCollectionOrphanCheckFilmCategory + " in its filmCategoryCollection field has a non-nullable category field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(category);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Category> findCategoryEntities() {
        return findCategoryEntities(true, -1, -1);
    }

    public List<Category> findCategoryEntities(int maxResults, int firstResult) {
        return findCategoryEntities(false, maxResults, firstResult);
    }

    private List<Category> findCategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Category.class));
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

    public Category findCategory(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Category> rt = cq.from(Category.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
