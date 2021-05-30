<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="model.User" %>
<%@ page import="javax.servlet.http.Cookie" %>

<!DOCTYPE html>



<html>

<head>
	<title>Nouveau Chat</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="style.css" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>
	<h2>Créer un chat</h2>
	<form class="gestion" action="ValidationChat" method="post">
		<label for="titre"> Nom du chat </label>
		<input type="text" id="titre" name="titre" required />
		<br>
		<br>
		<label for="description"> Description du chat </label>
		<input type="text" id="description" name="description" required />
		<br>
		<br>
		<label for="invite"> Invités dans le chat </label>

		<% out.println("<br>");
			ResultSet res = User.getAllUsers();
			Cookie[] cookies = request.getCookies();
			String login = "";
			for (Cookie cookie : cookies) {
			if (cookie.getName().equals("login"))
			login = cookie.getValue();
			}
			System.out.println("Login dans NouveauChat.js : " + login);
			String role=(String) request.getSession().getAttribute("role");

			out.println("<select id='liste' multiple size='5' name='invite'>");
				while (res.next()) {

					if (!login.equals(res.getString(1))){
					out.print("<option value='" + res.getString(1) + "'>");
						out.print(res.getString(1));
						out.println("</option>");
					}
				}

				out.println("</select>");

			out.println("<br>");
			%>
			
			<br>
			<label for="date_peremption"> Date de fin de validité </label> <input type="date"
				id="date_peremption" name="date_peremption" />
			<br>
			<br>
			<input id="btnVert" type="submit" value="Submit">
	</form>
	<br>
	<button id="btnGris" onclick="window.location.href = 'RedirectToHub'">Retour</button>

</body>

</html>