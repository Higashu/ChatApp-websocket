package serveur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

@ServerEndpoint(value = "/chatserver/{pseudo}/{ChatTitle}", configurator = ChatServer.EndpointConfigurator.class)
public class ChatServer {

	private static ChatServer singleton = new ChatServer();

	private ChatServer() {
	}

	// Acquisition de notre unique instance ChatServer
	public static ChatServer getInstance() {
		return ChatServer.singleton;
	}

	// On maintient toutes les sessions utilisateurs dans une collection.
	private Hashtable<String, Session> sessions = new Hashtable<>();

	// Cette m�thode est d�clench�e a chaque connexion d'un utilisateur.
	@OnOpen
	public void open(Session session, @PathParam("pseudo") String pseudo, @PathParam("ChatTitle") String ChatTitle) {
		session.getUserProperties().put("pseudo", pseudo);
		session.getUserProperties().put("ChatTitle", ChatTitle);
		sessions.put(session.getId(), session);
		this.sendListMember(ChatTitle);
		String fullMessage = "Vient de se connecter sur " + ChatTitle;
		this.handleMessage(fullMessage, session);
	}

	// Cette m�thode est d�clench�e a chaque d�connexion d'un utilisateur.
	@OnClose
	public void close(Session session) {
		sessions.remove(session.getId());
		String fullMessage = "Vient de se déconnecter";
		this.handleMessage(fullMessage, session);
		sendListMember(session.getUserProperties().get("ChatTitle").toString());
	}

	// Cette m�thode est d�clench�e en cas d'erreur de communication.
	@OnError
	public void onError(Throwable error) {
		System.out.println("Error: " + error.getMessage());
	}

	// Cette m�thode est d�clench�e a chaque r�ception d'un message utilisateur.
	@OnMessage
	public void handleMessage(String message, Session session) {
		String pseudo = (String) session.getUserProperties().get("pseudo");

		String fullMessage = "MSG:" + pseudo + " : " + message;

		sendMessage(fullMessage, session.getUserProperties().get("ChatTitle").toString());
	}

	// Une m�thode priv�e, sp�cifique a notre exemple. Elle permet l'envoie d'un
	// message aux participants de la discussion.
	private void sendMessage(String fullMessage, String chatName) {
		// Affichage sur la console du server Web.
		System.out.println(fullMessage);

		// On envoie le message a tout le monde.
		for (Session session : sessions.values()) {

			if (session.getUserProperties().get("ChatTitle").equals(chatName)) {
				try {
					session.getBasicRemote().sendText(fullMessage);
				} catch (Exception exception) {
					System.out.println("ERROR: cannot send message to " + session.getId());
				}
			}
		}
	}

	private void sendListMember(String ChatTitle) {

		ArrayList<String> members = new ArrayList<String>();

		for (Session session : sessions.values()) {
			members.add((String) session.getUserProperties().get("pseudo"));
		}

		for (Session session : sessions.values()) {

			if (session.getUserProperties().get("ChatTitle").equals(ChatTitle))
				try {
					session.getBasicRemote().sendText("INF:" + members.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}

		}

	}

	// Permet de ne pas avoir une instance differente par client. ChatServer est
	// donc g�rer en "singleton" et le configurateur utilise ce singleton.
	public static class EndpointConfigurator extends ServerEndpointConfig.Configurator {
		@Override
		@SuppressWarnings("unchecked")
		public <T> T getEndpointInstance(Class<T> endpointClass) {
			return (T) ChatServer.getInstance();
		}
	}
}