package com.corsojava.sakilajpa.cli;

import com.corsojava.sakilajpa.bo.FilmManagement;
import com.corsojava.sakilajpa.dao.FilmDao;
import com.corsojava.sakilajpa.model.Film;
import com.corsojava.sakilajpa.util.JpaUtil;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author <a href="mailto:lucio.benfante@gmail.com">Lucio Benfante</a>
 */
public class ListAllFilms {

    private FilmManagement filmManagement;

    public FilmManagement getFilmManagement() {
        return filmManagement;
    }

    public void setFilmManagement(FilmManagement filmManagement) {
        this.filmManagement = filmManagement;
    }
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dao.xml"});
        ((AbstractApplicationContext) context).registerShutdownHook();
        ListAllFilms listAllFilms = new ListAllFilms();
        context.getAutowireCapableBeanFactory()
                .autowireBeanProperties(listAllFilms,
                        AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,
                        true);
        listAllFilms.filmManagement.printAllFilms();
    }
    
    private static void getFilmsUsingJPQL() {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Film> query = em.createQuery("from Film f order by f.title", Film.class);
        List<Film> films = query.getResultList();
        for (Film film : films) {
            System.out.println(film.getTitle());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private static void getFilmsUsingNamedQueries() {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Film> query = em.createNamedQuery("Film.findAll", Film.class);
        List<Film> films = query.getResultList();
        for (Film film : films) {
            System.out.println(film.getTitle());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private static void getFilmsUsingCriteria() {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Film> cq = cb.createQuery(Film.class);
        Root<Film> root = cq.from(Film.class);
        cq.select(root);
        TypedQuery<Film> query = em.createQuery(cq);
        List<Film> films = query.getResultList();
        for (Film film : films) {
            System.out.println(film.getTitle());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

}
