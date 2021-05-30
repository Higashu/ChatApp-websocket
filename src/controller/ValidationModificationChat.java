package controller;

import model.Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;

@WebServlet(name = "ValidationModificationChat", urlPatterns = { "/ValidationModificationChat" })
public class ValidationModificationChat extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean valide = true;

		// Recuperation saisie utilisateur et cookie pour le login

		String titre = request.getParameter("titre");
		String description = request.getParameter("description");
		String[] invite = request.getParameterValues("invite");
		String date_peremption = request.getParameter("date_peremption");

		Cookie[] cookies = request.getCookies();
		String login = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("login"))
				login = cookie.getValue();
		}

		
		String nomChatInitiale = request.getParameter("ChatTitle"); 
		System.out.println(nomChatInitiale);
				
				
		// Verification saisie utilisateur

		if (titre == null || description == null || invite == null || date_peremption == null)
			valide = false;

		if ("".equals(titre) || "".equals(description) || "".equals(date_peremption))
			valide = false;

		try {

			if (Chat.findChatByTitle(titre) != null && !titre.equals(nomChatInitiale))
				valide = false;

		} catch (ClassNotFoundException | IOException | SQLException ex) {
			Logger.getLogger(ValidationModificationChat.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		if (valide) {
		
			LocalDate now = LocalDate.now();
			LocalDate peremption = LocalDate.parse(date_peremption);
			
			if (!peremption.isAfter(now))
				valide = false;
		}
		
		

		// Process de la requete

		if (valide) {

			try {
				
				Chat ancienChat = Chat.findChatByTitle(nomChatInitiale);
				ancienChat.delete();
				Chat nouveauChat = new Chat(titre.replaceAll("'", " "), login, description, date_peremption);
				nouveauChat.save();
				
				for (String element : invite)
					nouveauChat.addParticipant(element);
				
			} catch (ClassNotFoundException | IOException | SQLException ex) {
				Logger.getLogger(ValidationModificationChat.class.getName()).log(Level.SEVERE, null, ex);
			}


			this.getServletContext().getRequestDispatcher("/MesChats").forward(request, response);
		}

		// Si la saisie n'est pas correcte j'affiche la suite

		else {

			try (PrintWriter out = response.getWriter()) {

				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
		        out.println("<meta charset='UTF-8'>");
		        out.println("<link rel='stylesheet' href='style.css' />");
		        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
				out.println("<title>Servlet Validation</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Saisie incoreccte</h1>");
				out.println("<p>Les informations saisie ne sont pas correctes !</p>");
				out.println("<a href='MesChats'>Retourner vers la page mes chats</a>");
				out.println("<br>");
				out.println("<br>");
				out.println("<a href='RedirectToHub'>Retourner vers la page d'accueil</a>");
				out.println("</body>");
				out.println("</html>");
			}

		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
