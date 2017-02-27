<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert a Film</title>
</head>
<body>
	<h1>Nuovo/Modifica film</h1>
	<form:form commandName="film" action="save" method="POST">
		<div>
			<form:errors path="title">style="color: red"</form:errors>
		</div>
		<div>
			<form:label path="title">
				<spring:message code="title"></spring:message>
			</form:label>
			<form:input path="title" />
		</div>
		<div>
			<form:errors path="description"></form:errors>
		</div>
		<div>
			<form:label path="description">
				<spring:message code="description"></spring:message>
			</form:label>
			<form:textarea path="description" />
		</div>
		<div>
			<form:errors path="releaseYear"></form:errors>
		</div>
		<div>
			<form:label path="releaseYear">
				<spring:message code="releaseYear"></spring:message>
			</form:label>
			<form:input path="releaseYear" />
		</div>
		<div>
			<form:errors path="languageId"></form:errors>
		</div>
		<div>
			<form:label path="languageId">
				<spring:message code="language"></spring:message>
			</form:label>
			<form:select path="languageId" items="${languages}"
				itemValue="languageId" itemLabel="name" />
		</div>
		<div>
			<form:button value="Salva">Salva</form:button>
		</div>
	</form:form>

</body>
</html>