package controller;

import model.Chat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SupprimerChat", urlPatterns = { "/SupprimerChat" })
public class SupprimerChat extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ChatTitle = request.getParameter("ChatTitle");

		try {
			
			if (Chat.findChatByTitle(ChatTitle) != null)
				Chat.findChatByTitle(ChatTitle).delete();
			
		} catch (ClassNotFoundException | IOException | SQLException ex) {
			Logger.getLogger(SupprimerChat.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.getServletContext().getRequestDispatcher("/RedirectToHub").forward(request, response);

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
