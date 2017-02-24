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
import com.corsojava.sakilajpa.model.Staff;
import java.util.ArrayList;
import java.util.Collection;
import com.corsojava.sakilajpa.model.Inventory;
import com.corsojava.sakilajpa.model.Customer;
import com.corsojava.sakilajpa.model.Store;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class StoreJpaController implements Serializable {

    public StoreJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Store store) throws IllegalOrphanException {
        if (store.getStaffCollection() == null) {
            store.setStaffCollection(new ArrayList<Staff>());
        }
        if (store.getInventoryCollection() == null) {
            store.setInventoryCollection(new ArrayList<Inventory>());
        }
        if (store.getCustomerCollection() == null) {
            store.setCustomerCollection(new ArrayList<Customer>());
        }
        List<String> illegalOrphanMessages = null;
        Staff managerStaffIdOrphanCheck = store.getManagerStaffId();
        if (managerStaffIdOrphanCheck != null) {
            Store oldStoreIdOfManagerStaffId = managerStaffIdOrphanCheck.getStoreId();
            if (oldStoreIdOfManagerStaffId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Staff " + managerStaffIdOrphanCheck + " already has an item of type Store whose managerStaffId column cannot be null. Please make another selection for the managerStaffId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Address addressId = store.getAddressId();
            if (addressId != null) {
                addressId = em.getReference(addressId.getClass(), addressId.getAddressId());
                store.setAddressId(addressId);
            }
            Staff managerStaffId = store.getManagerStaffId();
            if (managerStaffId != null) {
                managerStaffId = em.getReference(managerStaffId.getClass(), managerStaffId.getStaffId());
                store.setManagerStaffId(managerStaffId);
            }
            Collection<Staff> attachedStaffCollection = new ArrayList<Staff>();
            for (Staff staffCollectionStaffToAttach : store.getStaffCollection()) {
                staffCollectionStaffToAttach = em.getReference(staffCollectionStaffToAttach.getClass(), staffCollectionStaffToAttach.getStaffId());
                attachedStaffCollection.add(staffCollectionStaffToAttach);
            }
            store.setStaffCollection(attachedStaffCollection);
            Collection<Inventory> attachedInventoryCollection = new ArrayList<Inventory>();
            for (Inventory inventoryCollectionInventoryToAttach : store.getInventoryCollection()) {
                inventoryCollectionInventoryToAttach = em.getReference(inventoryCollectionInventoryToAttach.getClass(), inventoryCollectionInventoryToAttach.getInventoryId());
                attachedInventoryCollection.add(inventoryCollectionInventoryToAttach);
            }
            store.setInventoryCollection(attachedInventoryCollection);
            Collection<Customer> attachedCustomerCollection = new ArrayList<Customer>();
            for (Customer customerCollectionCustomerToAttach : store.getCustomerCollection()) {
                customerCollectionCustomerToAttach = em.getReference(customerCollectionCustomerToAttach.getClass(), customerCollectionCustomerToAttach.getCustomerId());
                attachedCustomerCollection.add(customerCollectionCustomerToAttach);
            }
            store.setCustomerCollection(attachedCustomerCollection);
            em.persist(store);
            if (addressId != null) {
                addressId.getStoreCollection().add(store);
                addressId = em.merge(addressId);
            }
            if (managerStaffId != null) {
                managerStaffId.setStoreId(store);
                managerStaffId = em.merge(managerStaffId);
            }
            for (Staff staffCollectionStaff : store.getStaffCollection()) {
                Store oldStoreIdOfStaffCollectionStaff = staffCollectionStaff.getStoreId();
                staffCollectionStaff.setStoreId(store);
                staffCollectionStaff = em.merge(staffCollectionStaff);
                if (oldStoreIdOfStaffCollectionStaff != null) {
                    oldStoreIdOfStaffCollectionStaff.getStaffCollection().remove(staffCollectionStaff);
                    oldStoreIdOfStaffCollectionStaff = em.merge(oldStoreIdOfStaffCollectionStaff);
                }
            }
            for (Inventory inventoryCollectionInventory : store.getInventoryCollection()) {
                Store oldStoreIdOfInventoryCollectionInventory = inventoryCollectionInventory.getStoreId();
                inventoryCollectionInventory.setStoreId(store);
                inventoryCollectionInventory = em.merge(inventoryCollectionInventory);
                if (oldStoreIdOfInventoryCollectionInventory != null) {
                    oldStoreIdOfInventoryCollectionInventory.getInventoryCollection().remove(inventoryCollectionInventory);
                    oldStoreIdOfInventoryCollectionInventory = em.merge(oldStoreIdOfInventoryCollectionInventory);
                }
            }
            for (Customer customerCollectionCustomer : store.getCustomerCollection()) {
                Store oldStoreIdOfCustomerCollectionCustomer = customerCollectionCustomer.getStoreId();
                customerCollectionCustomer.setStoreId(store);
                customerCollectionCustomer = em.merge(customerCollectionCustomer);
                if (oldStoreIdOfCustomerCollectionCustomer != null) {
                    oldStoreIdOfCustomerCollectionCustomer.getCustomerCollection().remove(customerCollectionCustomer);
                    oldStoreIdOfCustomerCollectionCustomer = em.merge(oldStoreIdOfCustomerCollectionCustomer);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Store store) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Store persistentStore = em.find(Store.class, store.getStoreId());
            Address addressIdOld = persistentStore.getAddressId();
            Address addressIdNew = store.getAddressId();
            Staff managerStaffIdOld = persistentStore.getManagerStaffId();
            Staff managerStaffIdNew = store.getManagerStaffId();
            Collection<Staff> staffCollectionOld = persistentStore.getStaffCollection();
            Collection<Staff> staffCollectionNew = store.getStaffCollection();
            Collection<Inventory> inventoryCollectionOld = persistentStore.getInventoryCollection();
            Collection<Inventory> inventoryCollectionNew = store.getInventoryCollection();
            Collection<Customer> customerCollectionOld = persistentStore.getCustomerCollection();
            Collection<Customer> customerCollectionNew = store.getCustomerCollection();
            List<String> illegalOrphanMessages = null;
            if (managerStaffIdOld != null && !managerStaffIdOld.equals(managerStaffIdNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Staff " + managerStaffIdOld + " since its storeId field is not nullable.");
            }
            if (managerStaffIdNew != null && !managerStaffIdNew.equals(managerStaffIdOld)) {
                Store oldStoreIdOfManagerStaffId = managerStaffIdNew.getStoreId();
                if (oldStoreIdOfManagerStaffId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Staff " + managerStaffIdNew + " already has an item of type Store whose managerStaffId column cannot be null. Please make another selection for the managerStaffId field.");
                }
            }
            for (Staff staffCollectionOldStaff : staffCollectionOld) {
                if (!staffCollectionNew.contains(staffCollectionOldStaff)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Staff " + staffCollectionOldStaff + " since its storeId field is not nullable.");
                }
            }
            for (Inventory inventoryCollectionOldInventory : inventoryCollectionOld) {
                if (!inventoryCollectionNew.contains(inventoryCollectionOldInventory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inventory " + inventoryCollectionOldInventory + " since its storeId field is not nullable.");
                }
            }
            for (Customer customerCollectionOldCustomer : customerCollectionOld) {
                if (!customerCollectionNew.contains(customerCollectionOldCustomer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Customer " + customerCollectionOldCustomer + " since its storeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (addressIdNew != null) {
                addressIdNew = em.getReference(addressIdNew.getClass(), addressIdNew.getAddressId());
                store.setAddressId(addressIdNew);
            }
            if (managerStaffIdNew != null) {
                managerStaffIdNew = em.getReference(managerStaffIdNew.getClass(), managerStaffIdNew.getStaffId());
                store.setManagerStaffId(managerStaffIdNew);
            }
            Collection<Staff> attachedStaffCollectionNew = new ArrayList<Staff>();
            for (Staff staffCollectionNewStaffToAttach : staffCollectionNew) {
                staffCollectionNewStaffToAttach = em.getReference(staffCollectionNewStaffToAttach.getClass(), staffCollectionNewStaffToAttach.getStaffId());
                attachedStaffCollectionNew.add(staffCollectionNewStaffToAttach);
            }
            staffCollectionNew = attachedStaffCollectionNew;
            store.setStaffCollection(staffCollectionNew);
            Collection<Inventory> attachedInventoryCollectionNew = new ArrayList<Inventory>();
            for (Inventory inventoryCollectionNewInventoryToAttach : inventoryCollectionNew) {
                inventoryCollectionNewInventoryToAttach = em.getReference(inventoryCollectionNewInventoryToAttach.getClass(), inventoryCollectionNewInventoryToAttach.getInventoryId());
                attachedInventoryCollectionNew.add(inventoryCollectionNewInventoryToAttach);
            }
            inventoryCollectionNew = attachedInventoryCollectionNew;
            store.setInventoryCollection(inventoryCollectionNew);
            Collection<Customer> attachedCustomerCollectionNew = new ArrayList<Customer>();
            for (Customer customerCollectionNewCustomerToAttach : customerCollectionNew) {
                customerCollectionNewCustomerToAttach = em.getReference(customerCollectionNewCustomerToAttach.getClass(), customerCollectionNewCustomerToAttach.getCustomerId());
                attachedCustomerCollectionNew.add(customerCollectionNewCustomerToAttach);
            }
            customerCollectionNew = attachedCustomerCollectionNew;
            store.setCustomerCollection(customerCollectionNew);
            store = em.merge(store);
            if (addressIdOld != null && !addressIdOld.equals(addressIdNew)) {
                addressIdOld.getStoreCollection().remove(store);
                addressIdOld = em.merge(addressIdOld);
            }
            if (addressIdNew != null && !addressIdNew.equals(addressIdOld)) {
                addressIdNew.getStoreCollection().add(store);
                addressIdNew = em.merge(addressIdNew);
            }
            if (managerStaffIdNew != null && !managerStaffIdNew.equals(managerStaffIdOld)) {
                managerStaffIdNew.setStoreId(store);
                managerStaffIdNew = em.merge(managerStaffIdNew);
            }
            for (Staff staffCollectionNewStaff : staffCollectionNew) {
                if (!staffCollectionOld.contains(staffCollectionNewStaff)) {
                    Store oldStoreIdOfStaffCollectionNewStaff = staffCollectionNewStaff.getStoreId();
                    staffCollectionNewStaff.setStoreId(store);
                    staffCollectionNewStaff = em.merge(staffCollectionNewStaff);
                    if (oldStoreIdOfStaffCollectionNewStaff != null && !oldStoreIdOfStaffCollectionNewStaff.equals(store)) {
                        oldStoreIdOfStaffCollectionNewStaff.getStaffCollection().remove(staffCollectionNewStaff);
                        oldStoreIdOfStaffCollectionNewStaff = em.merge(oldStoreIdOfStaffCollectionNewStaff);
                    }
                }
            }
            for (Inventory inventoryCollectionNewInventory : inventoryCollectionNew) {
                if (!inventoryCollectionOld.contains(inventoryCollectionNewInventory)) {
                    Store oldStoreIdOfInventoryCollectionNewInventory = inventoryCollectionNewInventory.getStoreId();
                    inventoryCollectionNewInventory.setStoreId(store);
                    inventoryCollectionNewInventory = em.merge(inventoryCollectionNewInventory);
                    if (oldStoreIdOfInventoryCollectionNewInventory != null && !oldStoreIdOfInventoryCollectionNewInventory.equals(store)) {
                        oldStoreIdOfInventoryCollectionNewInventory.getInventoryCollection().remove(inventoryCollectionNewInventory);
                        oldStoreIdOfInventoryCollectionNewInventory = em.merge(oldStoreIdOfInventoryCollectionNewInventory);
                    }
                }
            }
            for (Customer customerCollectionNewCustomer : customerCollectionNew) {
                if (!customerCollectionOld.contains(customerCollectionNewCustomer)) {
                    Store oldStoreIdOfCustomerCollectionNewCustomer = customerCollectionNewCustomer.getStoreId();
                    customerCollectionNewCustomer.setStoreId(store);
                    customerCollectionNewCustomer = em.merge(customerCollectionNewCustomer);
                    if (oldStoreIdOfCustomerCollectionNewCustomer != null && !oldStoreIdOfCustomerCollectionNewCustomer.equals(store)) {
                        oldStoreIdOfCustomerCollectionNewCustomer.getCustomerCollection().remove(customerCollectionNewCustomer);
                        oldStoreIdOfCustomerCollectionNewCustomer = em.merge(oldStoreIdOfCustomerCollectionNewCustomer);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = store.getStoreId();
                if (findStore(id) == null) {
                    throw new NonexistentEntityException("The store with id " + id + " no longer exists.");
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
            Store store;
            try {
                store = em.getReference(Store.class, id);
                store.getStoreId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The store with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Staff managerStaffIdOrphanCheck = store.getManagerStaffId();
            if (managerStaffIdOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Store (" + store + ") cannot be destroyed since the Staff " + managerStaffIdOrphanCheck + " in its managerStaffId field has a non-nullable storeId field.");
            }
            Collection<Staff> staffCollectionOrphanCheck = store.getStaffCollection();
            for (Staff staffCollectionOrphanCheckStaff : staffCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Store (" + store + ") cannot be destroyed since the Staff " + staffCollectionOrphanCheckStaff + " in its staffCollection field has a non-nullable storeId field.");
            }
            Collection<Inventory> inventoryCollectionOrphanCheck = store.getInventoryCollection();
            for (Inventory inventoryCollectionOrphanCheckInventory : inventoryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Store (" + store + ") cannot be destroyed since the Inventory " + inventoryCollectionOrphanCheckInventory + " in its inventoryCollection field has a non-nullable storeId field.");
            }
            Collection<Customer> customerCollectionOrphanCheck = store.getCustomerCollection();
            for (Customer customerCollectionOrphanCheckCustomer : customerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Store (" + store + ") cannot be destroyed since the Customer " + customerCollectionOrphanCheckCustomer + " in its customerCollection field has a non-nullable storeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Address addressId = store.getAddressId();
            if (addressId != null) {
                addressId.getStoreCollection().remove(store);
                addressId = em.merge(addressId);
            }
            em.remove(store);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Store> findStoreEntities() {
        return findStoreEntities(true, -1, -1);
    }

    public List<Store> findStoreEntities(int maxResults, int firstResult) {
        return findStoreEntities(false, maxResults, firstResult);
    }

    private List<Store> findStoreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Store.class));
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

    public Store findStore(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Store.class, id);
        } finally {
            em.close();
        }
    }

    public int getStoreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Store> rt = cq.from(Store.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
