<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.corsojava.model.Film"%>
<%@page import="com.corsojava.dao.FilmDao"%>
<%@page import="com.corsojava.dao.SessionManager"%>
<%@page import="com.corsojava.dao.DaoFactory"%>
<%@page import="com.corsojava.dao.DaoFactoryInterface"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sakila</title>
</head>
<body>
	<%
		DaoFactoryInterface daoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC_DAO_FACTORY_TYPE);
		SessionManager sessionManager = daoFactory.getSessionManager();
		try {
			sessionManager.startConnection();
			sessionManager.startTransaction();
			FilmDao filmDao = daoFactory.getFilmDao();
			String title = "";
	%>
	<%=title = request.getParameter("title")%>

	<%
		Film film = new Film().withTitle(request.getParameter("title")).withDescription(request.getParameter("description")).withRelease_Year(request.getParameter("releaseYear")).withLength(Integer.parseInt(request.getParameter("length")));
			int result = filmDao.addFilm(film);
			sessionManager.commitTransaction();
		} catch (Exception e) {
			//sessionManager.rollbackTransaction();
		}

		sessionManager.releaseConnection();
	%>

	<form action="/sakila/AddFilmServlet" method=post>
		<center>
			<table cellpadding=4 cellspacing=2 border=0>
				<th bgcolor="#CCCCFF" colspan=2><font size=5>FILM INPUT</font>
					<br></th>
				<tr bgcolor="#c8d8f8">
					<td valign=top><b>Title</b> <br> <input type="text"
						name="title" value="" size=15 maxlength=20></td>
					<td valign=top><b>Description</b> <br> <input type="text"
						name="description" value="" size=15 maxlength=20></td>
				</tr>
				<tr bgcolor="#c8d8f8">
					<td valign=top><b>Release Year</b> <br> <input
						type="text" name="releaseYear" value="" size=25 maxlength=125>
						<br></td>
					<td valign=top><b>Length</b> <br> <input type="text"
						name="length" value="" size=5 maxlength=5></td>
				</tr>
				<br>
				<br>
				</td>
				</tr>
				<tr bgcolor="#c8d8f8">
					<td align=center colspan=2><input type="submit" value="Submit">
					</td>
				</tr>
			</table>
		</center>
	</form>
	<a href="http://localhost:8080/sakila/FilmSakilaServlet">Database Film</a>
</body>
</html>