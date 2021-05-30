package controller;

import model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "RedirectToHub", urlPatterns = { "/RedirectToHub" })
public class RedirectToHub extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		String login = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("login"))
				login = cookie.getValue();
			cookie.setMaxAge(45 * 60);
		}

		try {

			User user = User.findByLogin(login);

			HttpSession session = request.getSession();
			session.setAttribute("connected_user", user);
			session.setAttribute("login", user.getLogin());
			

			this.getServletContext().getRequestDispatcher("/Hub.jsp").forward(request, response);
			return;

		} catch (ClassNotFoundException | IOException | SQLException ex) {
			Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, ex.getStackTrace().toString(), ex);
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
