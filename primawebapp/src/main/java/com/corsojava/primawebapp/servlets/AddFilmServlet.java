package com.corsojava.primawebapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

/**
 * Servlet implementation class AddFilmServlet
 */
public class AddFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddFilmServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
		// DaoFactoryInterface daoFactory =
		// DaoFactory.getDaoFactory(DaoFactory.JDBC_DAO_FACTORY_TYPE);
		// SessionManager sessionManager = daoFactory.getSessionManager();
		// sessionManager.startConnection();
		// sessionManager.startTransaction();
		//
		// ActorDao actorDao = daoFactory.getActorDao();
		// List<Actor> actors = actorDao.getAllActors();
		// List<Actor> actorsToFilm = new LinkedList<Actor>();
		//
		// FilmDao filmDao = daoFactory.getFilmDao();
		// if (request.getParameter("currentFilm") != null) {
		// request.setAttribute("filmC",
		// filmDao.getFilm(Integer.parseInt(request.getParameter("currentFilm"))));
		// actorsToFilm =
		// (actorDao.findFilmActors(Integer.parseInt(request.getParameter("currentFilm"))));
		// request.setAttribute("actors", actorsToFilm);
		// } else {
		// if (request.getParameterValues("actor") != null) {
		// String[] ids = request.getParameterValues("actor");
		// for (int i = 0; i < ids.length; i++) {
		// actorsToFilm.add(actorDao.getActor(Integer.parseInt(ids[i])));
		// request.setAttribute("actors", actors);
		// }
		// }

		// }
		//
		// int language_id = 0;
		// if (request.getParameter("language_id") != null) {
		// language_id = Integer.parseInt(request.getParameter("language_id"));
		// } else {
		// language_id = 1;
		// }
		//
		// if (request.getParameter("lengthFilm") != null &&
		// !actorsToFilm.isEmpty()) {
		// Film film = new Film().withTitle(request.getParameter("titleFilm"))
		// .withDescription(request.getParameter("descriptionFilm"))
		// .withLength(Integer.parseInt(request.getParameter("lengthFilm")))
		// .withRelease_Year(request.getParameter("releaseYear")).withLanguage(language_id,
		// "roba")
		// .withOriginal_Language(1).withActors(actorsToFilm);
		//
		// filmDao.addFilm(film);
		// }
		// sessionManager.commitTransaction();
		// RequestDispatcher rd =
		// request.getRequestDispatcher("/WEB-INF/jsp/addFilm.jsp");
		// try {
		// rd.forward(request, response);
		// } catch (ServletException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } finally {
		// sessionManager.releaseConnection();
		// }
		ApplicationContext context = (ApplicationContext) this.getServletContext().getAttribute("SPRING_CONTEXT");

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

}
