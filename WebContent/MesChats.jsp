<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Chat" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="style.css" />
	<title>Liste de mes chats</title>
</head>

<body>
	<h1>Mes chats</h1>
	<form method='POST' action='SupprimerChat'>
		<ul>
			
			<% 
			
				@SuppressWarnings("unchecked") ArrayList<Chat> listeChat = (ArrayList<Chat>)
				request.getSession().getAttribute("my_chats");

				for ( Chat element : listeChat ){
					out.println("<li>Se connecter sur <a target='_blank' href='ConnexionChat?ChatTitle="+element.getTitle()+"'>" + element.getTitle() +"</a></li>");
					out.println("<br>");
					out.println("<a href='ModifierChat?ChatTitle=" + element.getTitle()+"'> Modifier " + element.getTitle()+"</a>");					
					out.println("<br>");
					out.println("<a href='SupprimerChat?ChatTitle=" + element.getTitle()+"'> Supprimer " + element.getTitle()+"</a>");
					out.println("<br>");
					out.println("<br>");
					out.println("<br>");
				}

			%>

		</ul>
	</form>
	<button id="btnGris" onclick="window.location.href = 'RedirectToHub'">Revenir à l'accueil</button>
</body>
</html>