package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Chat;

@WebServlet(name = "ModifierChat", urlPatterns = { "/ModifierChat" })
public class ModifierChat extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			String ChatTitle = request.getParameter("ChatTitle").toString();
			
			Cookie[] cookies = request.getCookies();
			String login = "";
			
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("login"))
				login = cookie.getValue();
			}					
			
			try {
				boolean loginProprietaire = Chat.findChatByTitle(ChatTitle).getOwner().equals(login);
				
				if (!loginProprietaire) {
					
					HttpSession session = request.getSession();
					session.setAttribute("my_chats", Chat.getAllChatOwnedByLogin(login));
					this.getServletContext().getRequestDispatcher("/MesChats.jsp").forward(request, response);
					return;
				}
			
			} catch (ClassNotFoundException | IOException | SQLException ex) {
				Logger.getLogger(ModifierChat.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			
			HttpSession session = request.getSession();
			session.setAttribute("ChatTitle", ChatTitle);
			session.setAttribute("login", login);
			this.getServletContext().getRequestDispatcher("/ModificationChat.jsp").forward(request, response);

		
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
