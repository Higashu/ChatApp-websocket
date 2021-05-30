package controller;

import model.Chat;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MesChats", urlPatterns = { "/MesChats" })
public class MesChats extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		String login = "";
		
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("login"))
				login = cookie.getValue();
		}
		

		try {

			ArrayList<Chat> listeChat = Chat.getAllChatOwnedByLogin(login);
			HttpSession session = request.getSession();

			session.setAttribute("my_chats", listeChat);

		} catch (ClassNotFoundException | IOException | SQLException ex) {
			Logger.getLogger(MesChats.class.getName()).log(Level.SEVERE, null, ex);
		}

		this.getServletContext().getRequestDispatcher("/MesChats.jsp").forward(request, response);
		return;
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
