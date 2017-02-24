package com.corsojava.sakilajpa.cli;

import com.corsojava.sakilajpa.model.Film;
import com.corsojava.sakilajpa.util.JpaUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class ListSelectedFilms {

    public static void main(String[] args) {
//        getFilmsUsingJPQL("%love%");
//        getFilmsUsingNamedQueries("%love%");
        getFilmsUsingCriteria("%love%");
    }

    private static void getFilmsUsingJPQL(String title) {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Film> query = em.createQuery("from Film f where upper(f.title) like upper(:title) order by f.title", Film.class);
        query.setParameter("title", title);
        List<Film> films = query.getResultList();
        for (Film film : films) {
            System.out.println(film.getTitle());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private static void getFilmsUsingNamedQueries(String title) {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Film> query = em.createNamedQuery("Film.findByPartialTitle", Film.class);
        query.setParameter("title", title);
        List<Film> films = query.getResultList();
        for (Film film : films) {
            System.out.println(film.getTitle());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private static void getFilmsUsingCriteria(String title) {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Film> cq = cb.createQuery(Film.class);
        Root<Film> root = cq.from(Film.class);
        cq.where(cb.like(root.<String>get("title"), cb.parameter(String.class, "title")));
        TypedQuery<Film> query = em.createQuery(cq);
        query.setParameter("title", title);
        List<Film> films = query.getResultList();
        for (Film film : films) {
            System.out.println(film.getTitle());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private static void getFilmsUsingCriteria(String title, int anno, int lunghezza) {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Film> cq = cb.createQuery(Film.class);
        Root<Film> root = cq.from(Film.class);
        List<Predicate> conditions = new ArrayList<Predicate>();
        if (title != null) {
            conditions.add(cb.like(root.<String>get("title"), cb.parameter(String.class, "title")));
        }
        if (anno != 0) {
            conditions.add(cb.equal(root.<String>get("releaseYear"), cb.parameter(Integer.class, "anno")));
        }
        if (lunghezza != 0) {
            conditions.add(cb.equal(root.<String>get("length"), cb.parameter(Integer.class, "lunghezza")));
        }
        cq.where(cb.and(conditions.toArray(new Predicate[conditions.size()])));
        TypedQuery<Film> query = em.createQuery(cq);
        query.setParameter("title", title);
        List<Film> films = query.getResultList();
        for (Film film : films) {
            System.out.println(film.getTitle());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
    
}
