package controller;

import model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Validation", urlPatterns = { "/Validation" })
public class Validation extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean valid = true;

        response.setContentType("text/html;charset=UTF-8");

        try {

            String firstName = request.getParameter("User first name");
            String lastName = request.getParameter("User familly name");
            String login = request.getParameter("Login");
            String gender = request.getParameter("gender");
            String password = request.getParameter("User password");

            if (firstName == null || lastName == null || login == null || password == null)
                valid = false;

            else if ("".equals(firstName) || "".equals(lastName) || "".equals(login) || "".equals(password))
                valid = false;

            else if (!gender.equals("M") && !gender.equals("F"))
                valid = false;

            if (User.findByLogin(login) != null)
                valid = false;

            if (valid) {

                User user = new User(lastName, firstName, login, gender, password);
                user.save();
                
                RequestDispatcher rd = request.getRequestDispatcher("validation.html");
                rd.forward(request, response);

            } else {

                System.out.println("Des champs sont mal renseignes");
                RequestDispatcher rd = request.getRequestDispatcher("erreurSaisie.html");
                rd.forward(request, response);
                return;

            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
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
