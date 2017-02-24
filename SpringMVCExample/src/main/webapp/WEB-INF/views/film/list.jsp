<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Film</h1>
	<spring:message code="filmNumberMessage" argumentSeparator="." arguments="${fn:length(films)}"/>
	<c:forEach var="film" items="${films}">
		<spring:message code="title" text="Titolo">${film.title}</spring:message>
		<a href="show/${film.filmId}">(Show)</a>
		<a href="edit/${film.filmId}">(Edit)</a>
	</c:forEach>
</body>
</html>