<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css" />
    <title>hub</title>
</head>

<body>
	
    <div class="info"> 
        <h2>Mes informations personnelles</h2>

        <h3>Nom : ${connected_user.getLastName()} </h3>

        <h3>Prénom : ${connected_user.getFirstName()}</h3>

        <h3>Login : ${connected_user.getLogin()} </h3>

    </div>
    <br>
    <div class="gestion">
        <h2>Gestion des chats</h2>
        <button id="btnVert" onclick="window.location.href = 'NouveauChat.jsp'">Créer un chat</button>
        <br><br>
        <button id="btnVert" onclick="window.location.href = 'MesChats'">Mes chats</button>
        <br><br>		
        <button id="btnVert" onclick="window.location.href = 'ChatsInvites'">Mes invitations</button>
    </div>
	<br>
    <form method="POST" action="Logout">
        <input type="hidden" name="action" value="Logout">
        <button id="btnRouge" class="logout">Logout</button>
    </form>

</body>

</html>