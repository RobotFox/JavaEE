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

import com.corsojava.dao.ActorDao;
import com.corsojava.dao.DaoFactory;
import com.corsojava.dao.DaoFactoryInterface;
import com.corsojava.dao.FilmDao;
import com.corsojava.dao.LanguageDao;
import com.corsojava.dao.SessionManager;
import com.corsojava.model.Actor;
import com.corsojava.model.Film;

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
		DaoFactoryInterface daoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC_DAO_FACTORY_TYPE);
		SessionManager sessionManager = daoFactory.getSessionManager();
		sessionManager.startConnection();
		sessionManager.startTransaction();

		System.out.println(request.getAttribute("javax.servlet.forward.request_uri"));

		FilmDao filmDao = daoFactory.getFilmDao();
		ActorDao actorDao = daoFactory.getActorDao();
		List<Actor> actors = actorDao.getAllActors();
		List<Actor> actorsToFilm = new LinkedList<Actor>();
		request.setAttribute("actors", actors);

		if (request.getParameterValues("actor") != null) {
			String[] ids = request.getParameterValues("actor");
			for (int i = 0; i < ids.length; i++) {
				actorsToFilm.add(actorDao.getActor(Integer.parseInt(ids[i])));
			}
		}

		int language_id = 0;
		if (request.getParameter("language_id") != null) {
			language_id = Integer.parseInt(request.getParameter("language_id"));
		} else {
			language_id = 1;
		}

		if (request.getParameter("lengthFilm") != null && !actorsToFilm.isEmpty()) {
			Film film = new Film().withTitle(request.getParameter("titleFilm"))
					.withDescription(request.getParameter("descriptionFilm"))
					.withLength(Integer.parseInt(request.getParameter("lengthFilm")))
					.withRelease_Year(request.getParameter("releaseYear")).withLanguage(language_id, "roba")
					.withOriginal_Language(1).withActors(actorsToFilm);

			filmDao.addFilm(film);
		}
		sessionManager.commitTransaction();
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/addFilm.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sessionManager.releaseConnection();
		}

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
