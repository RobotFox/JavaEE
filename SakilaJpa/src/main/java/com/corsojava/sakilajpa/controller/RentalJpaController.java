/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corsojava.sakilajpa.controller;

import com.corsojava.sakilajpa.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.corsojava.sakilajpa.model.Customer;
import com.corsojava.sakilajpa.model.Inventory;
import com.corsojava.sakilajpa.model.Staff;
import com.corsojava.sakilajpa.model.Payment;
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
public class RentalJpaController implements Serializable {

    public RentalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rental rental) {
        if (rental.getPaymentCollection() == null) {
            rental.setPaymentCollection(new ArrayList<Payment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customerId = rental.getCustomerId();
            if (customerId != null) {
                customerId = em.getReference(customerId.getClass(), customerId.getCustomerId());
                rental.setCustomerId(customerId);
            }
            Inventory inventoryId = rental.getInventoryId();
            if (inventoryId != null) {
                inventoryId = em.getReference(inventoryId.getClass(), inventoryId.getInventoryId());
                rental.setInventoryId(inventoryId);
            }
            Staff staffId = rental.getStaffId();
            if (staffId != null) {
                staffId = em.getReference(staffId.getClass(), staffId.getStaffId());
                rental.setStaffId(staffId);
            }
            Collection<Payment> attachedPaymentCollection = new ArrayList<Payment>();
            for (Payment paymentCollectionPaymentToAttach : rental.getPaymentCollection()) {
                paymentCollectionPaymentToAttach = em.getReference(paymentCollectionPaymentToAttach.getClass(), paymentCollectionPaymentToAttach.getPaymentId());
                attachedPaymentCollection.add(paymentCollectionPaymentToAttach);
            }
            rental.setPaymentCollection(attachedPaymentCollection);
            em.persist(rental);
            if (customerId != null) {
                customerId.getRentalCollection().add(rental);
                customerId = em.merge(customerId);
            }
            if (inventoryId != null) {
                inventoryId.getRentalCollection().add(rental);
                inventoryId = em.merge(inventoryId);
            }
            if (staffId != null) {
                staffId.getRentalCollection().add(rental);
                staffId = em.merge(staffId);
            }
            for (Payment paymentCollectionPayment : rental.getPaymentCollection()) {
                Rental oldRentalIdOfPaymentCollectionPayment = paymentCollectionPayment.getRentalId();
                paymentCollectionPayment.setRentalId(rental);
                paymentCollectionPayment = em.merge(paymentCollectionPayment);
                if (oldRentalIdOfPaymentCollectionPayment != null) {
                    oldRentalIdOfPaymentCollectionPayment.getPaymentCollection().remove(paymentCollectionPayment);
                    oldRentalIdOfPaymentCollectionPayment = em.merge(oldRentalIdOfPaymentCollectionPayment);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rental rental) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rental persistentRental = em.find(Rental.class, rental.getRentalId());
            Customer customerIdOld = persistentRental.getCustomerId();
            Customer customerIdNew = rental.getCustomerId();
            Inventory inventoryIdOld = persistentRental.getInventoryId();
            Inventory inventoryIdNew = rental.getInventoryId();
            Staff staffIdOld = persistentRental.getStaffId();
            Staff staffIdNew = rental.getStaffId();
            Collection<Payment> paymentCollectionOld = persistentRental.getPaymentCollection();
            Collection<Payment> paymentCollectionNew = rental.getPaymentCollection();
            if (customerIdNew != null) {
                customerIdNew = em.getReference(customerIdNew.getClass(), customerIdNew.getCustomerId());
                rental.setCustomerId(customerIdNew);
            }
            if (inventoryIdNew != null) {
                inventoryIdNew = em.getReference(inventoryIdNew.getClass(), inventoryIdNew.getInventoryId());
                rental.setInventoryId(inventoryIdNew);
            }
            if (staffIdNew != null) {
                staffIdNew = em.getReference(staffIdNew.getClass(), staffIdNew.getStaffId());
                rental.setStaffId(staffIdNew);
            }
            Collection<Payment> attachedPaymentCollectionNew = new ArrayList<Payment>();
            for (Payment paymentCollectionNewPaymentToAttach : paymentCollectionNew) {
                paymentCollectionNewPaymentToAttach = em.getReference(paymentCollectionNewPaymentToAttach.getClass(), paymentCollectionNewPaymentToAttach.getPaymentId());
                attachedPaymentCollectionNew.add(paymentCollectionNewPaymentToAttach);
            }
            paymentCollectionNew = attachedPaymentCollectionNew;
            rental.setPaymentCollection(paymentCollectionNew);
            rental = em.merge(rental);
            if (customerIdOld != null && !customerIdOld.equals(customerIdNew)) {
                customerIdOld.getRentalCollection().remove(rental);
                customerIdOld = em.merge(customerIdOld);
            }
            if (customerIdNew != null && !customerIdNew.equals(customerIdOld)) {
                customerIdNew.getRentalCollection().add(rental);
                customerIdNew = em.merge(customerIdNew);
            }
            if (inventoryIdOld != null && !inventoryIdOld.equals(inventoryIdNew)) {
                inventoryIdOld.getRentalCollection().remove(rental);
                inventoryIdOld = em.merge(inventoryIdOld);
            }
            if (inventoryIdNew != null && !inventoryIdNew.equals(inventoryIdOld)) {
                inventoryIdNew.getRentalCollection().add(rental);
                inventoryIdNew = em.merge(inventoryIdNew);
            }
            if (staffIdOld != null && !staffIdOld.equals(staffIdNew)) {
                staffIdOld.getRentalCollection().remove(rental);
                staffIdOld = em.merge(staffIdOld);
            }
            if (staffIdNew != null && !staffIdNew.equals(staffIdOld)) {
                staffIdNew.getRentalCollection().add(rental);
                staffIdNew = em.merge(staffIdNew);
            }
            for (Payment paymentCollectionOldPayment : paymentCollectionOld) {
                if (!paymentCollectionNew.contains(paymentCollectionOldPayment)) {
                    paymentCollectionOldPayment.setRentalId(null);
                    paymentCollectionOldPayment = em.merge(paymentCollectionOldPayment);
                }
            }
            for (Payment paymentCollectionNewPayment : paymentCollectionNew) {
                if (!paymentCollectionOld.contains(paymentCollectionNewPayment)) {
                    Rental oldRentalIdOfPaymentCollectionNewPayment = paymentCollectionNewPayment.getRentalId();
                    paymentCollectionNewPayment.setRentalId(rental);
                    paymentCollectionNewPayment = em.merge(paymentCollectionNewPayment);
                    if (oldRentalIdOfPaymentCollectionNewPayment != null && !oldRentalIdOfPaymentCollectionNewPayment.equals(rental)) {
                        oldRentalIdOfPaymentCollectionNewPayment.getPaymentCollection().remove(paymentCollectionNewPayment);
                        oldRentalIdOfPaymentCollectionNewPayment = em.merge(oldRentalIdOfPaymentCollectionNewPayment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rental.getRentalId();
                if (findRental(id) == null) {
                    throw new NonexistentEntityException("The rental with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rental rental;
            try {
                rental = em.getReference(Rental.class, id);
                rental.getRentalId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rental with id " + id + " no longer exists.", enfe);
            }
            Customer customerId = rental.getCustomerId();
            if (customerId != null) {
                customerId.getRentalCollection().remove(rental);
                customerId = em.merge(customerId);
            }
            Inventory inventoryId = rental.getInventoryId();
            if (inventoryId != null) {
                inventoryId.getRentalCollection().remove(rental);
                inventoryId = em.merge(inventoryId);
            }
            Staff staffId = rental.getStaffId();
            if (staffId != null) {
                staffId.getRentalCollection().remove(rental);
                staffId = em.merge(staffId);
            }
            Collection<Payment> paymentCollection = rental.getPaymentCollection();
            for (Payment paymentCollectionPayment : paymentCollection) {
                paymentCollectionPayment.setRentalId(null);
                paymentCollectionPayment = em.merge(paymentCollectionPayment);
            }
            em.remove(rental);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rental> findRentalEntities() {
        return findRentalEntities(true, -1, -1);
    }

    public List<Rental> findRentalEntities(int maxResults, int firstResult) {
        return findRentalEntities(false, maxResults, firstResult);
    }

    private List<Rental> findRentalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rental.class));
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

    public Rental findRental(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rental.class, id);
        } finally {
            em.close();
        }
    }

    public int getRentalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rental> rt = cq.from(Rental.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
