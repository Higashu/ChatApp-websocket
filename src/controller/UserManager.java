
package controller;

import model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserManager", urlPatterns = { "/UserManager" })
public class UserManager extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try {

            String firstName = request.getParameter("User first name");
            String lastName = request.getParameter("User familly name");
            String login = request.getParameter("Login");
            String gender = request.getParameter("gender");
            String password = request.getParameter("User password");
            User user = new User(lastName, firstName, login, gender, password);
            if (request.getParameter("role") != null) {
                user.setRole(User.Role.valueOf(request.getParameter("role")));
            }
            user.save();
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Un nouveau utilisateur </title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1> Un nouveau utilisateur est ajoute : </h1>");
                out.println(user.toString());
                out.println("</body>");
                out.println("</html>");

            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Un nouveau utilisateur </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Liste des utilisateurs : </h1>");
            out.println("<ol>");
            List<User> listUser;
            try {
                listUser = User.findAll();
                for (int index = 0; index < listUser.size(); index++) {
                    out.println("<li>");
                    out.println(listUser.get(index).toString());
                    out.println("</li>");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("</ol>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
