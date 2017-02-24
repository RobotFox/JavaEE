/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corsojava.sakilajpa.controller;

import com.corsojava.sakilajpa.controller.exceptions.IllegalOrphanException;
import com.corsojava.sakilajpa.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.corsojava.sakilajpa.model.Film;
import com.corsojava.sakilajpa.model.Inventory;
import com.corsojava.sakilajpa.model.Store;
import com.corsojava.sakilajpa.model.Rental;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class InventoryJpaController implements Serializable {

    public InventoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inventory inventory) {
        if (inventory.getRentalCollection() == null) {
            inventory.setRentalCollection(new ArrayList<Rental>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Film filmId = inventory.getFilmId();
            if (filmId != null) {
                filmId = em.getReference(filmId.getClass(), filmId.getFilmId());
                inventory.setFilmId(filmId);
            }
            Store storeId = inventory.getStoreId();
            if (storeId != null) {
                storeId = em.getReference(storeId.getClass(), storeId.getStoreId());
                inventory.setStoreId(storeId);
            }
            Collection<Rental> attachedRentalCollection = new ArrayList<Rental>();
            for (Rental rentalCollectionRentalToAttach : inventory.getRentalCollection()) {
                rentalCollectionRentalToAttach = em.getReference(rentalCollectionRentalToAttach.getClass(), rentalCollectionRentalToAttach.getRentalId());
                attachedRentalCollection.add(rentalCollectionRentalToAttach);
            }
            inventory.setRentalCollection(attachedRentalCollection);
            em.persist(inventory);
            if (filmId != null) {
                filmId.getInventoryCollection().add(inventory);
                filmId = em.merge(filmId);
            }
            if (storeId != null) {
                storeId.getInventoryCollection().add(inventory);
                storeId = em.merge(storeId);
            }
            for (Rental rentalCollectionRental : inventory.getRentalCollection()) {
                Inventory oldInventoryIdOfRentalCollectionRental = rentalCollectionRental.getInventoryId();
                rentalCollectionRental.setInventoryId(inventory);
                rentalCollectionRental = em.merge(rentalCollectionRental);
                if (oldInventoryIdOfRentalCollectionRental != null) {
                    oldInventoryIdOfRentalCollectionRental.getRentalCollection().remove(rentalCollectionRental);
                    oldInventoryIdOfRentalCollectionRental = em.merge(oldInventoryIdOfRentalCollectionRental);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inventory inventory) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventory persistentInventory = em.find(Inventory.class, inventory.getInventoryId());
            Film filmIdOld = persistentInventory.getFilmId();
            Film filmIdNew = inventory.getFilmId();
            Store storeIdOld = persistentInventory.getStoreId();
            Store storeIdNew = inventory.getStoreId();
            Collection<Rental> rentalCollectionOld = persistentInventory.getRentalCollection();
            Collection<Rental> rentalCollectionNew = inventory.getRentalCollection();
            List<String> illegalOrphanMessages = null;
            for (Rental rentalCollectionOldRental : rentalCollectionOld) {
                if (!rentalCollectionNew.contains(rentalCollectionOldRental)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rental " + rentalCollectionOldRental + " since its inventoryId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (filmIdNew != null) {
                filmIdNew = em.getReference(filmIdNew.getClass(), filmIdNew.getFilmId());
                inventory.setFilmId(filmIdNew);
            }
            if (storeIdNew != null) {
                storeIdNew = em.getReference(storeIdNew.getClass(), storeIdNew.getStoreId());
                inventory.setStoreId(storeIdNew);
            }
            Collection<Rental> attachedRentalCollectionNew = new ArrayList<Rental>();
            for (Rental rentalCollectionNewRentalToAttach : rentalCollectionNew) {
                rentalCollectionNewRentalToAttach = em.getReference(rentalCollectionNewRentalToAttach.getClass(), rentalCollectionNewRentalToAttach.getRentalId());
                attachedRentalCollectionNew.add(rentalCollectionNewRentalToAttach);
            }
            rentalCollectionNew = attachedRentalCollectionNew;
            inventory.setRentalCollection(rentalCollectionNew);
            inventory = em.merge(inventory);
            if (filmIdOld != null && !filmIdOld.equals(filmIdNew)) {
                filmIdOld.getInventoryCollection().remove(inventory);
                filmIdOld = em.merge(filmIdOld);
            }
            if (filmIdNew != null && !filmIdNew.equals(filmIdOld)) {
                filmIdNew.getInventoryCollection().add(inventory);
                filmIdNew = em.merge(filmIdNew);
            }
            if (storeIdOld != null && !storeIdOld.equals(storeIdNew)) {
                storeIdOld.getInventoryCollection().remove(inventory);
                storeIdOld = em.merge(storeIdOld);
            }
            if (storeIdNew != null && !storeIdNew.equals(storeIdOld)) {
                storeIdNew.getInventoryCollection().add(inventory);
                storeIdNew = em.merge(storeIdNew);
            }
            for (Rental rentalCollectionNewRental : rentalCollectionNew) {
                if (!rentalCollectionOld.contains(rentalCollectionNewRental)) {
                    Inventory oldInventoryIdOfRentalCollectionNewRental = rentalCollectionNewRental.getInventoryId();
                    rentalCollectionNewRental.setInventoryId(inventory);
                    rentalCollectionNewRental = em.merge(rentalCollectionNewRental);
                    if (oldInventoryIdOfRentalCollectionNewRental != null && !oldInventoryIdOfRentalCollectionNewRental.equals(inventory)) {
                        oldInventoryIdOfRentalCollectionNewRental.getRentalCollection().remove(rentalCollectionNewRental);
                        oldInventoryIdOfRentalCollectionNewRental = em.merge(oldInventoryIdOfRentalCollectionNewRental);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inventory.getInventoryId();
                if (findInventory(id) == null) {
                    throw new NonexistentEntityException("The inventory with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventory inventory;
            try {
                inventory = em.getReference(Inventory.class, id);
                inventory.getInventoryId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventory with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Rental> rentalCollectionOrphanCheck = inventory.getRentalCollection();
            for (Rental rentalCollectionOrphanCheckRental : rentalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inventory (" + inventory + ") cannot be destroyed since the Rental " + rentalCollectionOrphanCheckRental + " in its rentalCollection field has a non-nullable inventoryId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Film filmId = inventory.getFilmId();
            if (filmId != null) {
                filmId.getInventoryCollection().remove(inventory);
                filmId = em.merge(filmId);
            }
            Store storeId = inventory.getStoreId();
            if (storeId != null) {
                storeId.getInventoryCollection().remove(inventory);
                storeId = em.merge(storeId);
            }
            em.remove(inventory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inventory> findInventoryEntities() {
        return findInventoryEntities(true, -1, -1);
    }

    public List<Inventory> findInventoryEntities(int maxResults, int firstResult) {
        return findInventoryEntities(false, maxResults, firstResult);
    }

    private List<Inventory> findInventoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inventory.class));
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

    public Inventory findInventory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventory.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inventory> rt = cq.from(Inventory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
