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
import com.corsojava.sakilajpa.model.Address;
import com.corsojava.sakilajpa.model.Customer;
import com.corsojava.sakilajpa.model.Store;
import com.corsojava.sakilajpa.model.Rental;
import java.util.ArrayList;
import java.util.Collection;
import com.corsojava.sakilajpa.model.Payment;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class CustomerJpaController implements Serializable {

    public CustomerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customer customer) {
        if (customer.getRentalCollection() == null) {
            customer.setRentalCollection(new ArrayList<Rental>());
        }
        if (customer.getPaymentCollection() == null) {
            customer.setPaymentCollection(new ArrayList<Payment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Address addressId = customer.getAddressId();
            if (addressId != null) {
                addressId = em.getReference(addressId.getClass(), addressId.getAddressId());
                customer.setAddressId(addressId);
            }
            Store storeId = customer.getStoreId();
            if (storeId != null) {
                storeId = em.getReference(storeId.getClass(), storeId.getStoreId());
                customer.setStoreId(storeId);
            }
            Collection<Rental> attachedRentalCollection = new ArrayList<Rental>();
            for (Rental rentalCollectionRentalToAttach : customer.getRentalCollection()) {
                rentalCollectionRentalToAttach = em.getReference(rentalCollectionRentalToAttach.getClass(), rentalCollectionRentalToAttach.getRentalId());
                attachedRentalCollection.add(rentalCollectionRentalToAttach);
            }
            customer.setRentalCollection(attachedRentalCollection);
            Collection<Payment> attachedPaymentCollection = new ArrayList<Payment>();
            for (Payment paymentCollectionPaymentToAttach : customer.getPaymentCollection()) {
                paymentCollectionPaymentToAttach = em.getReference(paymentCollectionPaymentToAttach.getClass(), paymentCollectionPaymentToAttach.getPaymentId());
                attachedPaymentCollection.add(paymentCollectionPaymentToAttach);
            }
            customer.setPaymentCollection(attachedPaymentCollection);
            em.persist(customer);
            if (addressId != null) {
                addressId.getCustomerCollection().add(customer);
                addressId = em.merge(addressId);
            }
            if (storeId != null) {
                storeId.getCustomerCollection().add(customer);
                storeId = em.merge(storeId);
            }
            for (Rental rentalCollectionRental : customer.getRentalCollection()) {
                Customer oldCustomerIdOfRentalCollectionRental = rentalCollectionRental.getCustomerId();
                rentalCollectionRental.setCustomerId(customer);
                rentalCollectionRental = em.merge(rentalCollectionRental);
                if (oldCustomerIdOfRentalCollectionRental != null) {
                    oldCustomerIdOfRentalCollectionRental.getRentalCollection().remove(rentalCollectionRental);
                    oldCustomerIdOfRentalCollectionRental = em.merge(oldCustomerIdOfRentalCollectionRental);
                }
            }
            for (Payment paymentCollectionPayment : customer.getPaymentCollection()) {
                Customer oldCustomerIdOfPaymentCollectionPayment = paymentCollectionPayment.getCustomerId();
                paymentCollectionPayment.setCustomerId(customer);
                paymentCollectionPayment = em.merge(paymentCollectionPayment);
                if (oldCustomerIdOfPaymentCollectionPayment != null) {
                    oldCustomerIdOfPaymentCollectionPayment.getPaymentCollection().remove(paymentCollectionPayment);
                    oldCustomerIdOfPaymentCollectionPayment = em.merge(oldCustomerIdOfPaymentCollectionPayment);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customer customer) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer persistentCustomer = em.find(Customer.class, customer.getCustomerId());
            Address addressIdOld = persistentCustomer.getAddressId();
            Address addressIdNew = customer.getAddressId();
            Store storeIdOld = persistentCustomer.getStoreId();
            Store storeIdNew = customer.getStoreId();
            Collection<Rental> rentalCollectionOld = persistentCustomer.getRentalCollection();
            Collection<Rental> rentalCollectionNew = customer.getRentalCollection();
            Collection<Payment> paymentCollectionOld = persistentCustomer.getPaymentCollection();
            Collection<Payment> paymentCollectionNew = customer.getPaymentCollection();
            List<String> illegalOrphanMessages = null;
            for (Rental rentalCollectionOldRental : rentalCollectionOld) {
                if (!rentalCollectionNew.contains(rentalCollectionOldRental)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rental " + rentalCollectionOldRental + " since its customerId field is not nullable.");
                }
            }
            for (Payment paymentCollectionOldPayment : paymentCollectionOld) {
                if (!paymentCollectionNew.contains(paymentCollectionOldPayment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Payment " + paymentCollectionOldPayment + " since its customerId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (addressIdNew != null) {
                addressIdNew = em.getReference(addressIdNew.getClass(), addressIdNew.getAddressId());
                customer.setAddressId(addressIdNew);
            }
            if (storeIdNew != null) {
                storeIdNew = em.getReference(storeIdNew.getClass(), storeIdNew.getStoreId());
                customer.setStoreId(storeIdNew);
            }
            Collection<Rental> attachedRentalCollectionNew = new ArrayList<Rental>();
            for (Rental rentalCollectionNewRentalToAttach : rentalCollectionNew) {
                rentalCollectionNewRentalToAttach = em.getReference(rentalCollectionNewRentalToAttach.getClass(), rentalCollectionNewRentalToAttach.getRentalId());
                attachedRentalCollectionNew.add(rentalCollectionNewRentalToAttach);
            }
            rentalCollectionNew = attachedRentalCollectionNew;
            customer.setRentalCollection(rentalCollectionNew);
            Collection<Payment> attachedPaymentCollectionNew = new ArrayList<Payment>();
            for (Payment paymentCollectionNewPaymentToAttach : paymentCollectionNew) {
                paymentCollectionNewPaymentToAttach = em.getReference(paymentCollectionNewPaymentToAttach.getClass(), paymentCollectionNewPaymentToAttach.getPaymentId());
                attachedPaymentCollectionNew.add(paymentCollectionNewPaymentToAttach);
            }
            paymentCollectionNew = attachedPaymentCollectionNew;
            customer.setPaymentCollection(paymentCollectionNew);
            customer = em.merge(customer);
            if (addressIdOld != null && !addressIdOld.equals(addressIdNew)) {
                addressIdOld.getCustomerCollection().remove(customer);
                addressIdOld = em.merge(addressIdOld);
            }
            if (addressIdNew != null && !addressIdNew.equals(addressIdOld)) {
                addressIdNew.getCustomerCollection().add(customer);
                addressIdNew = em.merge(addressIdNew);
            }
            if (storeIdOld != null && !storeIdOld.equals(storeIdNew)) {
                storeIdOld.getCustomerCollection().remove(customer);
                storeIdOld = em.merge(storeIdOld);
            }
            if (storeIdNew != null && !storeIdNew.equals(storeIdOld)) {
                storeIdNew.getCustomerCollection().add(customer);
                storeIdNew = em.merge(storeIdNew);
            }
            for (Rental rentalCollectionNewRental : rentalCollectionNew) {
                if (!rentalCollectionOld.contains(rentalCollectionNewRental)) {
                    Customer oldCustomerIdOfRentalCollectionNewRental = rentalCollectionNewRental.getCustomerId();
                    rentalCollectionNewRental.setCustomerId(customer);
                    rentalCollectionNewRental = em.merge(rentalCollectionNewRental);
                    if (oldCustomerIdOfRentalCollectionNewRental != null && !oldCustomerIdOfRentalCollectionNewRental.equals(customer)) {
                        oldCustomerIdOfRentalCollectionNewRental.getRentalCollection().remove(rentalCollectionNewRental);
                        oldCustomerIdOfRentalCollectionNewRental = em.merge(oldCustomerIdOfRentalCollectionNewRental);
                    }
                }
            }
            for (Payment paymentCollectionNewPayment : paymentCollectionNew) {
                if (!paymentCollectionOld.contains(paymentCollectionNewPayment)) {
                    Customer oldCustomerIdOfPaymentCollectionNewPayment = paymentCollectionNewPayment.getCustomerId();
                    paymentCollectionNewPayment.setCustomerId(customer);
                    paymentCollectionNewPayment = em.merge(paymentCollectionNewPayment);
                    if (oldCustomerIdOfPaymentCollectionNewPayment != null && !oldCustomerIdOfPaymentCollectionNewPayment.equals(customer)) {
                        oldCustomerIdOfPaymentCollectionNewPayment.getPaymentCollection().remove(paymentCollectionNewPayment);
                        oldCustomerIdOfPaymentCollectionNewPayment = em.merge(oldCustomerIdOfPaymentCollectionNewPayment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = customer.getCustomerId();
                if (findCustomer(id) == null) {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
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
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getCustomerId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Rental> rentalCollectionOrphanCheck = customer.getRentalCollection();
            for (Rental rentalCollectionOrphanCheckRental : rentalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Rental " + rentalCollectionOrphanCheckRental + " in its rentalCollection field has a non-nullable customerId field.");
            }
            Collection<Payment> paymentCollectionOrphanCheck = customer.getPaymentCollection();
            for (Payment paymentCollectionOrphanCheckPayment : paymentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Payment " + paymentCollectionOrphanCheckPayment + " in its paymentCollection field has a non-nullable customerId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Address addressId = customer.getAddressId();
            if (addressId != null) {
                addressId.getCustomerCollection().remove(customer);
                addressId = em.merge(addressId);
            }
            Store storeId = customer.getStoreId();
            if (storeId != null) {
                storeId.getCustomerCollection().remove(customer);
                storeId = em.merge(storeId);
            }
            em.remove(customer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
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

    public Customer findCustomer(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
