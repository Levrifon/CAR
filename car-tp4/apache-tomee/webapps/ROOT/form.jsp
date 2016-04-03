<html>

<%

String author = request.getParameter("author");
if ( author != null ) {
    String title = request.getParameter("title");
    String year = request.getParameter("year");
    out.print("Auteur : "+author+"<br/>");
    out.print("Titre : "+title+"<br/>");
    out.print("Ann&eacute; : "+year+"<br/><p/>");
    
    out.print("<form action=\"form.jsp\">");
		out.print("Auteur : <input type=\"text\" name=\"author\" value=\""+author+"\"><br/>");
		out.print("Titre : <input type=\"text\" name=\"title\" value=\""+title+"\"><br/>");
		out.print("Ann&eacute;e : <input type=\"text\" name=\"year\" value=\""+year+"\"><br/>");
		out.print("<input type=\"submit\">");
	out.print("</form>");
} else {
	out.print("<form action=\"form.jsp\">");
		out.print("Auteur : <input type=\"text\" name=\"author\"><br/>");
		out.print("Titre : <input type=\"text\" name=\"title\" ><br/>");
		out.print("Ann&eacute;e : <input type=\"text\" name=\"year\"><br/>");
		out.print("<input type=\"submit\">");
	out.print("</form>");
}

%>

</html>
