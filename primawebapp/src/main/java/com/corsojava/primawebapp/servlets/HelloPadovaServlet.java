package com.corsojava.primawebapp.servlets;

import java.io.IOException;
import java.util.Iterator;
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
import com.corsojava.dao.SessionManager;
import com.corsojava.model.Film;

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
		DaoFactoryInterface daoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC_DAO_FACTORY_TYPE);
		SessionManager sessionManager = daoFactory.getSessionManager();
		try {
			sessionManager.startConnection();
			sessionManager.startTransaction();
			FilmDao filmDao = daoFactory.getFilmDao();
			List<Film> films = filmDao.getAllFilms();
			sessionManager.commitTransaction();
			request.setAttribute("films", films);
		} catch (Exception e) {
			sessionManager.rollbackTransaction();
		} finally {
			sessionManager.releaseConnection();
		}
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
