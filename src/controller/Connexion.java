package controller;

import model.User;
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
import javax.servlet.http.HttpSession;

@WebServlet(name = "Connexion", urlPatterns = { "/Connexion" })
public class Connexion extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			// V�rifier si le login existe
			
			User user = User.FindByloginAndPwd(request.getParameter("username"), request.getParameter("password"));
			if (user == null) {
				System.out.print("Connexion impossible, pas d'utilisateur correspondant avec ces identifiants\n");

				response.setContentType("text/html;charset=UTF-8");
				try (PrintWriter out = response.getWriter()) {
					out.println("<!DOCTYPE html>");
					out.println("<html>");
					out.println("<head>");
					out.println("<title>UT'Chat</title>");
				    out.println("<link rel='stylesheet' href='style.css' />");
					out.println("</head>");
					out.println("<body>");
					out.println("<h1>Echec login ou mot de passe erroné</h1>");
					out.println("<a href='connexion.html'>Revenir vers la page de connexion</a>");
					out.println("<br>");
					out.println("<a href='NouveauUtilisateur.html'>Créer un compte</a>");
					out.println("</body>");
					out.println("</html>");
				}

			} else {

				Cookie cookie = new Cookie("login", user.getLogin());
				cookie.setMaxAge(45 * 60);
				response.addCookie(cookie);

				HttpSession session = request.getSession();
				session.setAttribute("connected_user", user);
				session.setAttribute("login", user.getLogin());


				this.getServletContext().getRequestDispatcher("/Hub.jsp").forward(request, response);
				return;
			}

		} catch (ClassNotFoundException | SQLException ex) {
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