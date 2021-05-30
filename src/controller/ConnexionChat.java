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

@WebServlet(name = "ConnexionChat", urlPatterns = { "/ConnexionChat" })
public class ConnexionChat extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ChatTitle = request.getParameter("ChatTitle").toString();

		Cookie cookie = new Cookie("ChatTitle", ChatTitle);
		response.addCookie(cookie);
		
		HttpSession session = request.getSession();
		try {
			
			session.setAttribute("chat", Chat.findChatByTitle(ChatTitle));
			
		} catch (ClassNotFoundException | IOException | SQLException ex) {
			Logger.getLogger(ConnexionChat.class.getName()).log(Level.SEVERE, ex.getStackTrace().toString(), ex);
		}

		this.getServletContext().getRequestDispatcher("/Client.jsp").forward(request, response);

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
