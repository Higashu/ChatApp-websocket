<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="model.Chat" %>
<%@ page import="model.User" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>



<html>

<head>
	<title>Nouveau Chat</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="style.css" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>
	<h2>Modification de  ${ChatTitle}</h2>
	<form class="gestion" action="ValidationModificationChat" method="post">
		<label for="titre"> Nom du chat </label>
		<input type="text" id="titre" name="titre" value=<%out.print("'"+request.getSession().getAttribute("ChatTitle").toString()+"'"); %> required />
		<br>
		<br>
		<label for="description"> Description du chat </label>
		<input type="text" id="description" name="description"  value= <%out.print("'"+Chat.findChatByTitle(request.getSession().getAttribute("ChatTitle").toString()).getDescription()+"'"); %>required />
		<br>
		<br>
		<label for="invite"> Invités dans le chat </label>

		<% 
			out.println("<br>");						
			
			//Recuperer le login
			Cookie[] cookies = request.getCookies();
			String login = "";
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("login"))
				login = cookie.getValue();
			}
			
			Chat chat = Chat.findChatByTitle(request.getSession().getAttribute("ChatTitle").toString());
			ResultSet res = chat.getAllInvitedMembers();
			
			ArrayList<String> listeMembre = new ArrayList<String>();
			
			while (res.next()){
				listeMembre.add(res.getString(1));	
			}
			
			
			res = User.getAllUsers();
			

			out.println("<select id='liste' multiple size='5' name='invite'>");
			
				while (res.next()) {

					if (!login.equals(res.getString(1))){
						
						if (listeMembre.contains(res.getString(1)))
							out.print("<option selected value='" + res.getString(1) + "'>");
						
						else
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
				id="date_peremption" name="date_peremption" value=<%out.print("'"+Chat.findChatByTitle(request.getSession().getAttribute("ChatTitle").toString()).getDate_peremption()+"'"); %> />
			<input type="hidden" name="ChatTitle" value=<%out.print("'"+request.getSession().getAttribute("ChatTitle").toString()+"'"); %>>
			<br>			
			<br>
			<input id="btnVert" type="submit" value="Submit">
	</form>
	<br>
	<button id="btnGris" onclick="window.location.href = 'RedirectToHub'">Retour</button>

</body>

</html>