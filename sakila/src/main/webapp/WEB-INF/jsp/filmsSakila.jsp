<%@page import="com.corsojava.model.Film"%>
<%@page import="com.corsojava.dao.FilmDao"%>
<%@page import="com.corsojava.dao.SessionManager"%>
<%@page import="com.corsojava.dao.DaoFactory"%>
<%@page import="com.corsojava.dao.DaoFactoryInterface"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sakila</title>
</head>
<body>
	Welcome to the Sakila Database! This is the list of films on the
	database:
	<%
	DaoFactoryInterface daoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC_DAO_FACTORY_TYPE);
	SessionManager sessionManager = daoFactory.getSessionManager();
	try {
		sessionManager.startConnection();
		sessionManager.startTransaction();
		FilmDao filmDao = daoFactory.getFilmDao();
		List<Film> films = filmDao.getAllFilms();
		%>
		<table cellpadding="15" border="1" style="background-color: #ffffcc;">

		<%
		for (Film x : films) {
			%>
			<tr>
			<td>
			<%
			out.println(x.getTitle());
			%>
			</td>
			<td>
			<%
			out.println(x.getDescription());
			%>
			</td>
			<td>
			<%
			out.println(x.getRelease_year());
			%>
			</td>
			<td>
			<%
			out.println(x.getLength());
			%>
			</td>
			</tr>
			
			<%
		}
		%>
		</table>
		<%
		request.setAttribute("films", films);
		sessionManager.commitTransaction();
	} catch (Exception e) {
		//sessionManager.rollbackTransaction();
	}
	sessionManager.releaseConnection();
%>
</body>
</html>