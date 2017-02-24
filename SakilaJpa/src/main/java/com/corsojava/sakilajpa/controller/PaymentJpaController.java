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
import com.corsojava.sakilajpa.model.Payment;
import com.corsojava.sakilajpa.model.Rental;
import com.corsojava.sakilajpa.model.Staff;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class PaymentJpaController implements Serializable {

    public PaymentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Payment payment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customerId = payment.getCustomerId();
            if (customerId != null) {
                customerId = em.getReference(customerId.getClass(), customerId.getCustomerId());
                payment.setCustomerId(customerId);
            }
            Rental rentalId = payment.getRentalId();
            if (rentalId != null) {
                rentalId = em.getReference(rentalId.getClass(), rentalId.getRentalId());
                payment.setRentalId(rentalId);
            }
            Staff staffId = payment.getStaffId();
            if (staffId != null) {
                staffId = em.getReference(staffId.getClass(), staffId.getStaffId());
                payment.setStaffId(staffId);
            }
            em.persist(payment);
            if (customerId != null) {
                customerId.getPaymentCollection().add(payment);
                customerId = em.merge(customerId);
            }
            if (rentalId != null) {
                rentalId.getPaymentCollection().add(payment);
                rentalId = em.merge(rentalId);
            }
            if (staffId != null) {
                staffId.getPaymentCollection().add(payment);
                staffId = em.merge(staffId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Payment payment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Payment persistentPayment = em.find(Payment.class, payment.getPaymentId());
            Customer customerIdOld = persistentPayment.getCustomerId();
            Customer customerIdNew = payment.getCustomerId();
            Rental rentalIdOld = persistentPayment.getRentalId();
            Rental rentalIdNew = payment.getRentalId();
            Staff staffIdOld = persistentPayment.getStaffId();
            Staff staffIdNew = payment.getStaffId();
            if (customerIdNew != null) {
                customerIdNew = em.getReference(customerIdNew.getClass(), customerIdNew.getCustomerId());
                payment.setCustomerId(customerIdNew);
            }
            if (rentalIdNew != null) {
                rentalIdNew = em.getReference(rentalIdNew.getClass(), rentalIdNew.getRentalId());
                payment.setRentalId(rentalIdNew);
            }
            if (staffIdNew != null) {
                staffIdNew = em.getReference(staffIdNew.getClass(), staffIdNew.getStaffId());
                payment.setStaffId(staffIdNew);
            }
            payment = em.merge(payment);
            if (customerIdOld != null && !customerIdOld.equals(customerIdNew)) {
                customerIdOld.getPaymentCollection().remove(payment);
                customerIdOld = em.merge(customerIdOld);
            }
            if (customerIdNew != null && !customerIdNew.equals(customerIdOld)) {
                customerIdNew.getPaymentCollection().add(payment);
                customerIdNew = em.merge(customerIdNew);
            }
            if (rentalIdOld != null && !rentalIdOld.equals(rentalIdNew)) {
                rentalIdOld.getPaymentCollection().remove(payment);
                rentalIdOld = em.merge(rentalIdOld);
            }
            if (rentalIdNew != null && !rentalIdNew.equals(rentalIdOld)) {
                rentalIdNew.getPaymentCollection().add(payment);
                rentalIdNew = em.merge(rentalIdNew);
            }
            if (staffIdOld != null && !staffIdOld.equals(staffIdNew)) {
                staffIdOld.getPaymentCollection().remove(payment);
                staffIdOld = em.merge(staffIdOld);
            }
            if (staffIdNew != null && !staffIdNew.equals(staffIdOld)) {
                staffIdNew.getPaymentCollection().add(payment);
                staffIdNew = em.merge(staffIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = payment.getPaymentId();
                if (findPayment(id) == null) {
                    throw new NonexistentEntityException("The payment with id " + id + " no longer exists.");
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
            Payment payment;
            try {
                payment = em.getReference(Payment.class, id);
                payment.getPaymentId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The payment with id " + id + " no longer exists.", enfe);
            }
            Customer customerId = payment.getCustomerId();
            if (customerId != null) {
                customerId.getPaymentCollection().remove(payment);
                customerId = em.merge(customerId);
            }
            Rental rentalId = payment.getRentalId();
            if (rentalId != null) {
                rentalId.getPaymentCollection().remove(payment);
                rentalId = em.merge(rentalId);
            }
            Staff staffId = payment.getStaffId();
            if (staffId != null) {
                staffId.getPaymentCollection().remove(payment);
                staffId = em.merge(staffId);
            }
            em.remove(payment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Payment> findPaymentEntities() {
        return findPaymentEntities(true, -1, -1);
    }

    public List<Payment> findPaymentEntities(int maxResults, int firstResult) {
        return findPaymentEntities(false, maxResults, firstResult);
    }

    private List<Payment> findPaymentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Payment.class));
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

    public Payment findPayment(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Payment.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaymentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Payment> rt = cq.from(Payment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
