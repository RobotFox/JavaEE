package com.corsojava.primawebapp.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.corsojava.sakiladata.model.Actor;
import com.corsojava.sakiladata.model.Film;
import com.corsojava.sakiladata.model.FilmActor;
import com.corsojava.sakiladata.util.HibernateUtil;

/**
 * Servlet implementation class HelloPadovaServlet
 */
public class HelloPadovaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelloPadovaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Film");
		List<Film> resultList = query.getResultList();
		List<Film> films = new LinkedList<Film>();
		List<FilmActor> ac = new LinkedList<FilmActor>();
		if (request.getParameter("titleFilm") != null) {
			query = session.createQuery("from Film f where f.title LIKE :title");
			query.setParameter("title", "%" + request.getParameter("titleFilm") + "%");
			films = query.getResultList();
			request.setAttribute("films", films);
		} else {
			for (Film film : resultList) {
				for (FilmActor filmActor : film.getFilmActors()) {
					System.out.println(filmActor.getActor_id());
				}
			}

			request.setAttribute("films", resultList);
		}
		// films = filmDao.findFilms(new
		// Film().withTitle(request.getParameter("titleFilm")));
		// }
		// sessionManager.commitTransaction();

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/helloPadova.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		// Class.forName ecc.....
	}

}
