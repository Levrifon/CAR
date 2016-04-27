<%@ page pageEncoding="UTF-8" %>
<%@ page import="car.tp4.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>CAR TP4 - Exo 2</title>
		<link rel="stylesheet" href="/css/font-awesome.min.css">
		
		<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
	</head>
	<body class="container text-center" style="margin-top:80px;">
		<%
		int pSize = (Integer) request.getAttribute("p-size");
		%>
		<div class="row">
			<div class="col col-xs-4 pull-right">
				<div class="panel panel-primary">
					<div class="panel-heading">Votre panier d'achat</div>
					<div class="panel-body"><span style="background-color: red ! important;" class="text-danger badge"><% out.print(""+pSize); %></span> Items</div>
				</div>
			</div>
		</div>
		<h1 class="row">Liste des livres
		<%
		
		List<Book> livres = (List<Book>) request.getAttribute("LIST");
		if ( livres != null ) {
			if (livres.size() == 0) out.print("</h1><b>Bibliothèque vide !</b>");
			else out.print(" <span class=\"badge blue\">("+livres.size()+" livres)</span> </h1>");
		%>
		<div class="row text-center">
			<div class="col col-xs-12 col-md-6 col-md-offset-3">
				<form action="list.jsp" method="post">
					<table class="table table-hover table-bordered">
					  <tr>
					  	<th>#</th>
					    <th>Titre</th>
					    <th>Auteur</th>		
					    <th>Année</th>
					    <th>Ajouter au panier</th>
					  </tr>
		<%
			for(Book b : livres){
		%>
					  <tr>
					  	<td><% out.print(b.getId()); %> <input type="text" name="id" value="<% out.print(b.getId()); %>" class="sr-only"/></td>
					    <td><% out.print(b.getTitle()); %> <input type="text" name="author" value="<% out.print(b.getTitle()); %>" class="sr-only"/></td>
					    <td><% out.print(b.getAuthor()); %> <input type="text" name="title" value="<% out.print(b.getAuthor()); %>" class="sr-only"/></td>		
					    <td><% out.print(b.getYear()); %> <input type="text" name="year" value="<% out.print(b.getYear()); %>" class="sr-only"/></td>
					    <td>
					    	<button type="submit" class="btn btn-success">
							  Ajouter <i class="fa fa-plus-square-o"></i>
							</button>
					    </td>
					  </tr>
		<%
			}
		}
		else out.print("<b>Bibliothèque injoignable</b>");
		
		%>	
					</table>
				</form>	
			</div>
		</div>

		<a href="/index.jsp">Retour Accueil</a>
	</body>
</html>
