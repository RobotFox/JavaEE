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
import com.corsojava.sakilajpa.model.Store;
import com.corsojava.sakilajpa.model.Rental;
import java.util.ArrayList;
import java.util.Collection;
import com.corsojava.sakilajpa.model.Payment;
import com.corsojava.sakilajpa.model.Staff;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class StaffJpaController implements Serializable {

    public StaffJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Staff staff) {
        if (staff.getRentalCollection() == null) {
            staff.setRentalCollection(new ArrayList<Rental>());
        }
        if (staff.getPaymentCollection() == null) {
            staff.setPaymentCollection(new ArrayList<Payment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Address addressId = staff.getAddressId();
            if (addressId != null) {
                addressId = em.getReference(addressId.getClass(), addressId.getAddressId());
                staff.setAddressId(addressId);
            }
            Store storeId = staff.getStoreId();
            if (storeId != null) {
                storeId = em.getReference(storeId.getClass(), storeId.getStoreId());
                staff.setStoreId(storeId);
            }
            Store store = staff.getStore();
            if (store != null) {
                store = em.getReference(store.getClass(), store.getStoreId());
                staff.setStore(store);
            }
            Collection<Rental> attachedRentalCollection = new ArrayList<Rental>();
            for (Rental rentalCollectionRentalToAttach : staff.getRentalCollection()) {
                rentalCollectionRentalToAttach = em.getReference(rentalCollectionRentalToAttach.getClass(), rentalCollectionRentalToAttach.getRentalId());
                attachedRentalCollection.add(rentalCollectionRentalToAttach);
            }
            staff.setRentalCollection(attachedRentalCollection);
            Collection<Payment> attachedPaymentCollection = new ArrayList<Payment>();
            for (Payment paymentCollectionPaymentToAttach : staff.getPaymentCollection()) {
                paymentCollectionPaymentToAttach = em.getReference(paymentCollectionPaymentToAttach.getClass(), paymentCollectionPaymentToAttach.getPaymentId());
                attachedPaymentCollection.add(paymentCollectionPaymentToAttach);
            }
            staff.setPaymentCollection(attachedPaymentCollection);
            em.persist(staff);
            if (addressId != null) {
                addressId.getStaffCollection().add(staff);
                addressId = em.merge(addressId);
            }
            if (storeId != null) {
                storeId.getStaffCollection().add(staff);
                storeId = em.merge(storeId);
            }
            if (store != null) {
                Staff oldManagerStaffIdOfStore = store.getManagerStaffId();
                if (oldManagerStaffIdOfStore != null) {
                    oldManagerStaffIdOfStore.setStore(null);
                    oldManagerStaffIdOfStore = em.merge(oldManagerStaffIdOfStore);
                }
                store.setManagerStaffId(staff);
                store = em.merge(store);
            }
            for (Rental rentalCollectionRental : staff.getRentalCollection()) {
                Staff oldStaffIdOfRentalCollectionRental = rentalCollectionRental.getStaffId();
                rentalCollectionRental.setStaffId(staff);
                rentalCollectionRental = em.merge(rentalCollectionRental);
                if (oldStaffIdOfRentalCollectionRental != null) {
                    oldStaffIdOfRentalCollectionRental.getRentalCollection().remove(rentalCollectionRental);
                    oldStaffIdOfRentalCollectionRental = em.merge(oldStaffIdOfRentalCollectionRental);
                }
            }
            for (Payment paymentCollectionPayment : staff.getPaymentCollection()) {
                Staff oldStaffIdOfPaymentCollectionPayment = paymentCollectionPayment.getStaffId();
                paymentCollectionPayment.setStaffId(staff);
                paymentCollectionPayment = em.merge(paymentCollectionPayment);
                if (oldStaffIdOfPaymentCollectionPayment != null) {
                    oldStaffIdOfPaymentCollectionPayment.getPaymentCollection().remove(paymentCollectionPayment);
                    oldStaffIdOfPaymentCollectionPayment = em.merge(oldStaffIdOfPaymentCollectionPayment);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Staff staff) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Staff persistentStaff = em.find(Staff.class, staff.getStaffId());
            Address addressIdOld = persistentStaff.getAddressId();
            Address addressIdNew = staff.getAddressId();
            Store storeIdOld = persistentStaff.getStoreId();
            Store storeIdNew = staff.getStoreId();
            Store storeOld = persistentStaff.getStore();
            Store storeNew = staff.getStore();
            Collection<Rental> rentalCollectionOld = persistentStaff.getRentalCollection();
            Collection<Rental> rentalCollectionNew = staff.getRentalCollection();
            Collection<Payment> paymentCollectionOld = persistentStaff.getPaymentCollection();
            Collection<Payment> paymentCollectionNew = staff.getPaymentCollection();
            List<String> illegalOrphanMessages = null;
            if (storeOld != null && !storeOld.equals(storeNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Store " + storeOld + " since its managerStaffId field is not nullable.");
            }
            for (Rental rentalCollectionOldRental : rentalCollectionOld) {
                if (!rentalCollectionNew.contains(rentalCollectionOldRental)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rental " + rentalCollectionOldRental + " since its staffId field is not nullable.");
                }
            }
            for (Payment paymentCollectionOldPayment : paymentCollectionOld) {
                if (!paymentCollectionNew.contains(paymentCollectionOldPayment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Payment " + paymentCollectionOldPayment + " since its staffId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (addressIdNew != null) {
                addressIdNew = em.getReference(addressIdNew.getClass(), addressIdNew.getAddressId());
                staff.setAddressId(addressIdNew);
            }
            if (storeIdNew != null) {
                storeIdNew = em.getReference(storeIdNew.getClass(), storeIdNew.getStoreId());
                staff.setStoreId(storeIdNew);
            }
            if (storeNew != null) {
                storeNew = em.getReference(storeNew.getClass(), storeNew.getStoreId());
                staff.setStore(storeNew);
            }
            Collection<Rental> attachedRentalCollectionNew = new ArrayList<Rental>();
            for (Rental rentalCollectionNewRentalToAttach : rentalCollectionNew) {
                rentalCollectionNewRentalToAttach = em.getReference(rentalCollectionNewRentalToAttach.getClass(), rentalCollectionNewRentalToAttach.getRentalId());
                attachedRentalCollectionNew.add(rentalCollectionNewRentalToAttach);
            }
            rentalCollectionNew = attachedRentalCollectionNew;
            staff.setRentalCollection(rentalCollectionNew);
            Collection<Payment> attachedPaymentCollectionNew = new ArrayList<Payment>();
            for (Payment paymentCollectionNewPaymentToAttach : paymentCollectionNew) {
                paymentCollectionNewPaymentToAttach = em.getReference(paymentCollectionNewPaymentToAttach.getClass(), paymentCollectionNewPaymentToAttach.getPaymentId());
                attachedPaymentCollectionNew.add(paymentCollectionNewPaymentToAttach);
            }
            paymentCollectionNew = attachedPaymentCollectionNew;
            staff.setPaymentCollection(paymentCollectionNew);
            staff = em.merge(staff);
            if (addressIdOld != null && !addressIdOld.equals(addressIdNew)) {
                addressIdOld.getStaffCollection().remove(staff);
                addressIdOld = em.merge(addressIdOld);
            }
            if (addressIdNew != null && !addressIdNew.equals(addressIdOld)) {
                addressIdNew.getStaffCollection().add(staff);
                addressIdNew = em.merge(addressIdNew);
            }
            if (storeIdOld != null && !storeIdOld.equals(storeIdNew)) {
                storeIdOld.getStaffCollection().remove(staff);
                storeIdOld = em.merge(storeIdOld);
            }
            if (storeIdNew != null && !storeIdNew.equals(storeIdOld)) {
                storeIdNew.getStaffCollection().add(staff);
                storeIdNew = em.merge(storeIdNew);
            }
            if (storeNew != null && !storeNew.equals(storeOld)) {
                Staff oldManagerStaffIdOfStore = storeNew.getManagerStaffId();
                if (oldManagerStaffIdOfStore != null) {
                    oldManagerStaffIdOfStore.setStore(null);
                    oldManagerStaffIdOfStore = em.merge(oldManagerStaffIdOfStore);
                }
                storeNew.setManagerStaffId(staff);
                storeNew = em.merge(storeNew);
            }
            for (Rental rentalCollectionNewRental : rentalCollectionNew) {
                if (!rentalCollectionOld.contains(rentalCollectionNewRental)) {
                    Staff oldStaffIdOfRentalCollectionNewRental = rentalCollectionNewRental.getStaffId();
                    rentalCollectionNewRental.setStaffId(staff);
                    rentalCollectionNewRental = em.merge(rentalCollectionNewRental);
                    if (oldStaffIdOfRentalCollectionNewRental != null && !oldStaffIdOfRentalCollectionNewRental.equals(staff)) {
                        oldStaffIdOfRentalCollectionNewRental.getRentalCollection().remove(rentalCollectionNewRental);
                        oldStaffIdOfRentalCollectionNewRental = em.merge(oldStaffIdOfRentalCollectionNewRental);
                    }
                }
            }
            for (Payment paymentCollectionNewPayment : paymentCollectionNew) {
                if (!paymentCollectionOld.contains(paymentCollectionNewPayment)) {
                    Staff oldStaffIdOfPaymentCollectionNewPayment = paymentCollectionNewPayment.getStaffId();
                    paymentCollectionNewPayment.setStaffId(staff);
                    paymentCollectionNewPayment = em.merge(paymentCollectionNewPayment);
                    if (oldStaffIdOfPaymentCollectionNewPayment != null && !oldStaffIdOfPaymentCollectionNewPayment.equals(staff)) {
                        oldStaffIdOfPaymentCollectionNewPayment.getPaymentCollection().remove(paymentCollectionNewPayment);
                        oldStaffIdOfPaymentCollectionNewPayment = em.merge(oldStaffIdOfPaymentCollectionNewPayment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = staff.getStaffId();
                if (findStaff(id) == null) {
                    throw new NonexistentEntityException("The staff with id " + id + " no longer exists.");
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
            Staff staff;
            try {
                staff = em.getReference(Staff.class, id);
                staff.getStaffId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The staff with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Store storeOrphanCheck = staff.getStore();
            if (storeOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Staff (" + staff + ") cannot be destroyed since the Store " + storeOrphanCheck + " in its store field has a non-nullable managerStaffId field.");
            }
            Collection<Rental> rentalCollectionOrphanCheck = staff.getRentalCollection();
            for (Rental rentalCollectionOrphanCheckRental : rentalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Staff (" + staff + ") cannot be destroyed since the Rental " + rentalCollectionOrphanCheckRental + " in its rentalCollection field has a non-nullable staffId field.");
            }
            Collection<Payment> paymentCollectionOrphanCheck = staff.getPaymentCollection();
            for (Payment paymentCollectionOrphanCheckPayment : paymentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Staff (" + staff + ") cannot be destroyed since the Payment " + paymentCollectionOrphanCheckPayment + " in its paymentCollection field has a non-nullable staffId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Address addressId = staff.getAddressId();
            if (addressId != null) {
                addressId.getStaffCollection().remove(staff);
                addressId = em.merge(addressId);
            }
            Store storeId = staff.getStoreId();
            if (storeId != null) {
                storeId.getStaffCollection().remove(staff);
                storeId = em.merge(storeId);
            }
            em.remove(staff);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Staff> findStaffEntities() {
        return findStaffEntities(true, -1, -1);
    }

    public List<Staff> findStaffEntities(int maxResults, int firstResult) {
        return findStaffEntities(false, maxResults, firstResult);
    }

    private List<Staff> findStaffEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Staff.class));
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

    public Staff findStaff(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Staff.class, id);
        } finally {
            em.close();
        }
    }

    public int getStaffCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Staff> rt = cq.from(Staff.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
