package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.invalidate();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
        	
        	Cookie newCookie = new Cookie (cookie.getName(),"");
        	newCookie.setMaxAge(0);
            response.addCookie(newCookie);
        }
                

        System.out.println("Deconnexion");

        this.getServletContext().getRequestDispatcher("/connexion.html").forward(request, response);
        return;

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
