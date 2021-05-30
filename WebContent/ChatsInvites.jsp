<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Chat" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="style.css" />
	<title>Mes invitations</title>
</head>

<body>
	<h2>Mes invitations</h2>
	<ul>
		<% @SuppressWarnings("unchecked") ArrayList<Chat> listeChat = (ArrayList<Chat>)
				request.getSession().getAttribute("my_chats");

				for ( Chat element : listeChat ){
					out.println("<li>Se connecter sur <a href='ConnexionChat?ChatTitle="+element.getTitle()+"'>" + element.getTitle() +"</a></li>");
					out.println("<br>");
				}

				%>

	</ul>
	<button id="btnGris" onclick="window.location.href = 'RedirectToHub'">Revenir à l'accueil</button>
</body>

</html>