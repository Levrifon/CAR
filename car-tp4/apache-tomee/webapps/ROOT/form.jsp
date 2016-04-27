<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>CAR TP4 - Exo 1</title>
	</head>
	<body>
		<%
		
		String author = request.getParameter("author");
		if ( author != null ) {
		    String title = request.getParameter("title");
		    String year = request.getParameter("year");
		    out.print("Auteur : "+author+"<br/>");
		    out.print("Titre : "+title+"<br/>");
		    out.print("Ann&eacute; : "+year+"<br/><p/>");
		}
		
		%>
		<form action="form.jsp">
			Auteur : <input type="text" name="author"><br/>
			Titre : <input type="text" name="title"><br/>
			Ann&eacute;e : <input type="text" name="year"><br/>
			<input type="submit">
		</form>
	</body>
</html>
