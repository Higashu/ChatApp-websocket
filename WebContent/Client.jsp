<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Chat" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>UT'Chat</title>
    <link rel="stylesheet" href="style.css" />
    <script type="text/javascript" src="Client.js"></script>
</head>

<body>
	<h2>${chat.getTitle()}</h2>
	<p>${chat.getDescription() }</p>
    <textarea id="history" readonly></textarea>
    <input id="txtMessage" type="text" placeholder='Entrez votre message...'/>
    <button id="btnSend">Envoyer</button>
    <br>
    <br>
    <button id="btnClose">Fermer ce chat</button>
    <br>
    <br>
    <button id="btnGris" onclick="window.location.href = 'RedirectToHub'">Revenir à l'accueil</button>
    <br>
    <br>
    <br>
    <h2>Liste des utilisateurs connectés</h2>
    <ul id="listUser">
    </ul>
</body>

</html>