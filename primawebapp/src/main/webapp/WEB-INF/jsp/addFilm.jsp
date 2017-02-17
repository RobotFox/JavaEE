<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form action="/primawebapp/AddFilmServlet" method=post>
		<center>
			<table cellpadding=4 cellspacing=2 border=0>
				<th bgcolor="#CCCCFF" colspan=2><font size=5>FILM INPUT</font>
					<br></th>
				<tr bgcolor="#c8d8f8">
					<td valign=top><b>Title</b> <br> <input type="text"
						name="titleFilm" value="" size=15 maxlength=20></td>
					<td valign=top><b>Description</b> <br> <input type="text"
						name="descriptionFilm" value="" size=15 maxlength=20></td>
				</tr>
				<tr bgcolor="#c8d8f8">
					<td valign=top><b>Release Year</b> <br> <input
						type="text" name="releaseYear" value="" size=25 maxlength=125>
						<br></td>
					<td valign=top><b>Length</b> <br> <input type="text"
						name="lengthFilm" value="" size=5 maxlength=5></td>
				</tr>
				<tr>
					<td><select name="actor" multiple>
							<c:forEach var="actr" items="${actors}">
								<option value="${actr.id}">${actr.firstName}
									${actr.lastName}</option>
							</c:forEach>
					</select></td>
					<td><select>
							<option name="language_id" value="1">English</option>
							<option name="language_id" value="2">Italian</option>
							<option name="language_id" value="3">Japanese</option>
							<option name="language_id" value="4">Mandarin</option>
							<option name="language_id" value="5">French</option>
							<option name="language_id" value="6">German</option>
					</select></td>
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

</body>
</html>