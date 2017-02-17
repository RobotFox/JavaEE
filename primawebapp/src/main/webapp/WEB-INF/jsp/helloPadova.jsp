<%@page import="com.corsojava.model.Film"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista film:</title>
</head>
<body>
	Ci sono ${fn:length(films) } film.
	<table cellpadding="15" border="1" style="background-color: #ffffcc;">
		<TR>
			<TH COLSPAN="5">
				<H3>
					<BR>Titolo:
				</H3>
				<form action="/primawebapp/HelloPadovaServlet" method="GET">
					<input type="text" name="titleFilm"><br /> <input
						type="submit" value="Cerca">

				</form>
			</TH>
		</TR>
		<c:forEach var="film" items="${films}">
			<tr>
				<td>${film.title} <a
					href="http://localhost:8080/primawebapp/AddFilmServlet">(Modifica)</a></td>
				<td><c:out value="${film.description}"></c:out></td>
				<td><c:out value="${film.length}"></c:out></td>
				<td><c:out value="${film.release_year}"></c:out></td>
				<td><c:forEach var="actor" items="${film.actors}"
						varStatus="status">
						${actor.firstName} ${actor.lastName}<c:if test="${status.last}">.</c:if>
						<c:if test="${not status.last}">,</c:if>
					</c:forEach></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>