package com.corsojava.primawebapp.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import inc.sam.sakilaspringdata.jpa.ActorRepository;
import inc.sam.sakilaspringdata.main.SpringApplicationMainBean;
import inc.sam.sakilaspringdata.model.Actor;

/**
 * Servlet implementation class HelloPadovaServlet
 */
public class HelloPadovaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ActorRepository repository;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelloPadovaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationcontext.xml");
		ApplicationContext context = (ApplicationContext) this.getServletContext().getAttribute("SPRING_CONTEXT");
		SpringApplicationMainBean samb = (SpringApplicationMainBean) context.getBean("mainClass");
		List<Actor> actors = samb.getAllActors();
		for (Actor actor : actors) {
			System.out.println(actor.toString());
		}

		request.setAttribute("actors", actors);

		// ApplicationContext context = (ApplicationContext)
		// this.getServletContext().getAttribute("SPRING_CONTEXT");
		// SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		// Session session = sessionFactory.openSession();
		// Transaction tx = session.beginTransaction();
		// Query query = session.createQuery("from Film");
		// List<Film> resultList = query.getResultList();
		// List<Film> films = new LinkedList<Film>();
		// List<FilmActor> ac = new LinkedList<FilmActor>();
		// if (request.getParameter("titleFilm") != null) {
		// query = session.createQuery("from Film f where f.title LIKE :title");
		// query.setParameter("title", "%" + request.getParameter("titleFilm") +
		// "%");
		// films = query.getResultList();
		// request.setAttribute("films", films);
		// } else {
		// for (Film film : resultList) {
		// for (FilmActor filmActor : film.getFilmActors()) {
		// System.out.println(filmActor.getActor_id());
		// }
		// }
		//
		// request.setAttribute("films", resultList);
		// }
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
	}

}
