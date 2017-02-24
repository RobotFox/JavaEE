/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corsojava.sakilajpa.controller;

import com.corsojava.sakilajpa.controller.exceptions.IllegalOrphanException;
import com.corsojava.sakilajpa.controller.exceptions.NonexistentEntityException;
import com.corsojava.sakilajpa.model.Film;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.corsojava.sakilajpa.model.Language;
import com.corsojava.sakilajpa.model.FilmCategory;
import java.util.ArrayList;
import java.util.Collection;
import com.corsojava.sakilajpa.model.FilmActor;
import com.corsojava.sakilajpa.model.Inventory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class FilmJpaController implements Serializable {

    public FilmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Film film) {
        if (film.getFilmCategoryCollection() == null) {
            film.setFilmCategoryCollection(new ArrayList<FilmCategory>());
        }
        if (film.getFilmActorCollection() == null) {
            film.setFilmActorCollection(new ArrayList<FilmActor>());
        }
        if (film.getInventoryCollection() == null) {
            film.setInventoryCollection(new ArrayList<Inventory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Language languageId = film.getLanguageId();
            if (languageId != null) {
                languageId = em.getReference(languageId.getClass(), languageId.getLanguageId());
                film.setLanguageId(languageId);
            }
            Language originalLanguageId = film.getOriginalLanguageId();
            if (originalLanguageId != null) {
                originalLanguageId = em.getReference(originalLanguageId.getClass(), originalLanguageId.getLanguageId());
                film.setOriginalLanguageId(originalLanguageId);
            }
            Collection<FilmCategory> attachedFilmCategoryCollection = new ArrayList<FilmCategory>();
            for (FilmCategory filmCategoryCollectionFilmCategoryToAttach : film.getFilmCategoryCollection()) {
                filmCategoryCollectionFilmCategoryToAttach = em.getReference(filmCategoryCollectionFilmCategoryToAttach.getClass(), filmCategoryCollectionFilmCategoryToAttach.getFilmCategoryPK());
                attachedFilmCategoryCollection.add(filmCategoryCollectionFilmCategoryToAttach);
            }
            film.setFilmCategoryCollection(attachedFilmCategoryCollection);
            Collection<FilmActor> attachedFilmActorCollection = new ArrayList<FilmActor>();
            for (FilmActor filmActorCollectionFilmActorToAttach : film.getFilmActorCollection()) {
                filmActorCollectionFilmActorToAttach = em.getReference(filmActorCollectionFilmActorToAttach.getClass(), filmActorCollectionFilmActorToAttach.getFilmActorPK());
                attachedFilmActorCollection.add(filmActorCollectionFilmActorToAttach);
            }
            film.setFilmActorCollection(attachedFilmActorCollection);
            Collection<Inventory> attachedInventoryCollection = new ArrayList<Inventory>();
            for (Inventory inventoryCollectionInventoryToAttach : film.getInventoryCollection()) {
                inventoryCollectionInventoryToAttach = em.getReference(inventoryCollectionInventoryToAttach.getClass(), inventoryCollectionInventoryToAttach.getInventoryId());
                attachedInventoryCollection.add(inventoryCollectionInventoryToAttach);
            }
            film.setInventoryCollection(attachedInventoryCollection);
            em.persist(film);
            if (languageId != null) {
                languageId.getFilmCollection().add(film);
                languageId = em.merge(languageId);
            }
            if (originalLanguageId != null) {
                originalLanguageId.getFilmCollection().add(film);
                originalLanguageId = em.merge(originalLanguageId);
            }
            for (FilmCategory filmCategoryCollectionFilmCategory : film.getFilmCategoryCollection()) {
                Film oldFilmOfFilmCategoryCollectionFilmCategory = filmCategoryCollectionFilmCategory.getFilm();
                filmCategoryCollectionFilmCategory.setFilm(film);
                filmCategoryCollectionFilmCategory = em.merge(filmCategoryCollectionFilmCategory);
                if (oldFilmOfFilmCategoryCollectionFilmCategory != null) {
                    oldFilmOfFilmCategoryCollectionFilmCategory.getFilmCategoryCollection().remove(filmCategoryCollectionFilmCategory);
                    oldFilmOfFilmCategoryCollectionFilmCategory = em.merge(oldFilmOfFilmCategoryCollectionFilmCategory);
                }
            }
            for (FilmActor filmActorCollectionFilmActor : film.getFilmActorCollection()) {
                Film oldFilmOfFilmActorCollectionFilmActor = filmActorCollectionFilmActor.getFilm();
                filmActorCollectionFilmActor.setFilm(film);
                filmActorCollectionFilmActor = em.merge(filmActorCollectionFilmActor);
                if (oldFilmOfFilmActorCollectionFilmActor != null) {
                    oldFilmOfFilmActorCollectionFilmActor.getFilmActorCollection().remove(filmActorCollectionFilmActor);
                    oldFilmOfFilmActorCollectionFilmActor = em.merge(oldFilmOfFilmActorCollectionFilmActor);
                }
            }
            for (Inventory inventoryCollectionInventory : film.getInventoryCollection()) {
                Film oldFilmIdOfInventoryCollectionInventory = inventoryCollectionInventory.getFilmId();
                inventoryCollectionInventory.setFilmId(film);
                inventoryCollectionInventory = em.merge(inventoryCollectionInventory);
                if (oldFilmIdOfInventoryCollectionInventory != null) {
                    oldFilmIdOfInventoryCollectionInventory.getInventoryCollection().remove(inventoryCollectionInventory);
                    oldFilmIdOfInventoryCollectionInventory = em.merge(oldFilmIdOfInventoryCollectionInventory);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Film film) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Film persistentFilm = em.find(Film.class, film.getFilmId());
            Language languageIdOld = persistentFilm.getLanguageId();
            Language languageIdNew = film.getLanguageId();
            Language originalLanguageIdOld = persistentFilm.getOriginalLanguageId();
            Language originalLanguageIdNew = film.getOriginalLanguageId();
            Collection<FilmCategory> filmCategoryCollectionOld = persistentFilm.getFilmCategoryCollection();
            Collection<FilmCategory> filmCategoryCollectionNew = film.getFilmCategoryCollection();
            Collection<FilmActor> filmActorCollectionOld = persistentFilm.getFilmActorCollection();
            Collection<FilmActor> filmActorCollectionNew = film.getFilmActorCollection();
            Collection<Inventory> inventoryCollectionOld = persistentFilm.getInventoryCollection();
            Collection<Inventory> inventoryCollectionNew = film.getInventoryCollection();
            List<String> illegalOrphanMessages = null;
            for (FilmCategory filmCategoryCollectionOldFilmCategory : filmCategoryCollectionOld) {
                if (!filmCategoryCollectionNew.contains(filmCategoryCollectionOldFilmCategory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FilmCategory " + filmCategoryCollectionOldFilmCategory + " since its film field is not nullable.");
                }
            }
            for (FilmActor filmActorCollectionOldFilmActor : filmActorCollectionOld) {
                if (!filmActorCollectionNew.contains(filmActorCollectionOldFilmActor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FilmActor " + filmActorCollectionOldFilmActor + " since its film field is not nullable.");
                }
            }
            for (Inventory inventoryCollectionOldInventory : inventoryCollectionOld) {
                if (!inventoryCollectionNew.contains(inventoryCollectionOldInventory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inventory " + inventoryCollectionOldInventory + " since its filmId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (languageIdNew != null) {
                languageIdNew = em.getReference(languageIdNew.getClass(), languageIdNew.getLanguageId());
                film.setLanguageId(languageIdNew);
            }
            if (originalLanguageIdNew != null) {
                originalLanguageIdNew = em.getReference(originalLanguageIdNew.getClass(), originalLanguageIdNew.getLanguageId());
                film.setOriginalLanguageId(originalLanguageIdNew);
            }
            Collection<FilmCategory> attachedFilmCategoryCollectionNew = new ArrayList<FilmCategory>();
            for (FilmCategory filmCategoryCollectionNewFilmCategoryToAttach : filmCategoryCollectionNew) {
                filmCategoryCollectionNewFilmCategoryToAttach = em.getReference(filmCategoryCollectionNewFilmCategoryToAttach.getClass(), filmCategoryCollectionNewFilmCategoryToAttach.getFilmCategoryPK());
                attachedFilmCategoryCollectionNew.add(filmCategoryCollectionNewFilmCategoryToAttach);
            }
            filmCategoryCollectionNew = attachedFilmCategoryCollectionNew;
            film.setFilmCategoryCollection(filmCategoryCollectionNew);
            Collection<FilmActor> attachedFilmActorCollectionNew = new ArrayList<FilmActor>();
            for (FilmActor filmActorCollectionNewFilmActorToAttach : filmActorCollectionNew) {
                filmActorCollectionNewFilmActorToAttach = em.getReference(filmActorCollectionNewFilmActorToAttach.getClass(), filmActorCollectionNewFilmActorToAttach.getFilmActorPK());
                attachedFilmActorCollectionNew.add(filmActorCollectionNewFilmActorToAttach);
            }
            filmActorCollectionNew = attachedFilmActorCollectionNew;
            film.setFilmActorCollection(filmActorCollectionNew);
            Collection<Inventory> attachedInventoryCollectionNew = new ArrayList<Inventory>();
            for (Inventory inventoryCollectionNewInventoryToAttach : inventoryCollectionNew) {
                inventoryCollectionNewInventoryToAttach = em.getReference(inventoryCollectionNewInventoryToAttach.getClass(), inventoryCollectionNewInventoryToAttach.getInventoryId());
                attachedInventoryCollectionNew.add(inventoryCollectionNewInventoryToAttach);
            }
            inventoryCollectionNew = attachedInventoryCollectionNew;
            film.setInventoryCollection(inventoryCollectionNew);
            film = em.merge(film);
            if (languageIdOld != null && !languageIdOld.equals(languageIdNew)) {
                languageIdOld.getFilmCollection().remove(film);
                languageIdOld = em.merge(languageIdOld);
            }
            if (languageIdNew != null && !languageIdNew.equals(languageIdOld)) {
                languageIdNew.getFilmCollection().add(film);
                languageIdNew = em.merge(languageIdNew);
            }
            if (originalLanguageIdOld != null && !originalLanguageIdOld.equals(originalLanguageIdNew)) {
                originalLanguageIdOld.getFilmCollection().remove(film);
                originalLanguageIdOld = em.merge(originalLanguageIdOld);
            }
            if (originalLanguageIdNew != null && !originalLanguageIdNew.equals(originalLanguageIdOld)) {
                originalLanguageIdNew.getFilmCollection().add(film);
                originalLanguageIdNew = em.merge(originalLanguageIdNew);
            }
            for (FilmCategory filmCategoryCollectionNewFilmCategory : filmCategoryCollectionNew) {
                if (!filmCategoryCollectionOld.contains(filmCategoryCollectionNewFilmCategory)) {
                    Film oldFilmOfFilmCategoryCollectionNewFilmCategory = filmCategoryCollectionNewFilmCategory.getFilm();
                    filmCategoryCollectionNewFilmCategory.setFilm(film);
                    filmCategoryCollectionNewFilmCategory = em.merge(filmCategoryCollectionNewFilmCategory);
                    if (oldFilmOfFilmCategoryCollectionNewFilmCategory != null && !oldFilmOfFilmCategoryCollectionNewFilmCategory.equals(film)) {
                        oldFilmOfFilmCategoryCollectionNewFilmCategory.getFilmCategoryCollection().remove(filmCategoryCollectionNewFilmCategory);
                        oldFilmOfFilmCategoryCollectionNewFilmCategory = em.merge(oldFilmOfFilmCategoryCollectionNewFilmCategory);
                    }
                }
            }
            for (FilmActor filmActorCollectionNewFilmActor : filmActorCollectionNew) {
                if (!filmActorCollectionOld.contains(filmActorCollectionNewFilmActor)) {
                    Film oldFilmOfFilmActorCollectionNewFilmActor = filmActorCollectionNewFilmActor.getFilm();
                    filmActorCollectionNewFilmActor.setFilm(film);
                    filmActorCollectionNewFilmActor = em.merge(filmActorCollectionNewFilmActor);
                    if (oldFilmOfFilmActorCollectionNewFilmActor != null && !oldFilmOfFilmActorCollectionNewFilmActor.equals(film)) {
                        oldFilmOfFilmActorCollectionNewFilmActor.getFilmActorCollection().remove(filmActorCollectionNewFilmActor);
                        oldFilmOfFilmActorCollectionNewFilmActor = em.merge(oldFilmOfFilmActorCollectionNewFilmActor);
                    }
                }
            }
            for (Inventory inventoryCollectionNewInventory : inventoryCollectionNew) {
                if (!inventoryCollectionOld.contains(inventoryCollectionNewInventory)) {
                    Film oldFilmIdOfInventoryCollectionNewInventory = inventoryCollectionNewInventory.getFilmId();
                    inventoryCollectionNewInventory.setFilmId(film);
                    inventoryCollectionNewInventory = em.merge(inventoryCollectionNewInventory);
                    if (oldFilmIdOfInventoryCollectionNewInventory != null && !oldFilmIdOfInventoryCollectionNewInventory.equals(film)) {
                        oldFilmIdOfInventoryCollectionNewInventory.getInventoryCollection().remove(inventoryCollectionNewInventory);
                        oldFilmIdOfInventoryCollectionNewInventory = em.merge(oldFilmIdOfInventoryCollectionNewInventory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = film.getFilmId();
                if (findFilm(id) == null) {
                    throw new NonexistentEntityException("The film with id " + id + " no longer exists.");
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
            Film film;
            try {
                film = em.getReference(Film.class, id);
                film.getFilmId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The film with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<FilmCategory> filmCategoryCollectionOrphanCheck = film.getFilmCategoryCollection();
            for (FilmCategory filmCategoryCollectionOrphanCheckFilmCategory : filmCategoryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Film (" + film + ") cannot be destroyed since the FilmCategory " + filmCategoryCollectionOrphanCheckFilmCategory + " in its filmCategoryCollection field has a non-nullable film field.");
            }
            Collection<FilmActor> filmActorCollectionOrphanCheck = film.getFilmActorCollection();
            for (FilmActor filmActorCollectionOrphanCheckFilmActor : filmActorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Film (" + film + ") cannot be destroyed since the FilmActor " + filmActorCollectionOrphanCheckFilmActor + " in its filmActorCollection field has a non-nullable film field.");
            }
            Collection<Inventory> inventoryCollectionOrphanCheck = film.getInventoryCollection();
            for (Inventory inventoryCollectionOrphanCheckInventory : inventoryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Film (" + film + ") cannot be destroyed since the Inventory " + inventoryCollectionOrphanCheckInventory + " in its inventoryCollection field has a non-nullable filmId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Language languageId = film.getLanguageId();
            if (languageId != null) {
                languageId.getFilmCollection().remove(film);
                languageId = em.merge(languageId);
            }
            Language originalLanguageId = film.getOriginalLanguageId();
            if (originalLanguageId != null) {
                originalLanguageId.getFilmCollection().remove(film);
                originalLanguageId = em.merge(originalLanguageId);
            }
            em.remove(film);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Film> findFilmEntities() {
        return findFilmEntities(true, -1, -1);
    }

    public List<Film> findFilmEntities(int maxResults, int firstResult) {
        return findFilmEntities(false, maxResults, firstResult);
    }

    private List<Film> findFilmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Film.class));
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

    public Film findFilm(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Film.class, id);
        } finally {
            em.close();
        }
    }

    public int getFilmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Film> rt = cq.from(Film.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
