package servlet;

import entity.user.User;
import entity.user.UserDao;
import entity.user.UserDaoFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class LoginServlet extends HttpServlet {

    private UserDao userDao = UserDaoFactory.getUserDao();
    private boolean failed = false;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String reset = request.getParameter("reset");
        request.getRequestDispatcher("pageHeader.html").include(request, response);
        if(failed) {
            out.println("<div class='alert alert-danger'><strong>Login Failed.</strong> Email/Password combination is incorrect.</div>");
        }

        request.getRequestDispatcher("login.html").include(request, response);
        request.getRequestDispatcher("pageFooter.html").include(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        User attemptedUserLogin = userDao.getUserByEmail(request.getParameter("email"));

        if(attemptedUserLogin != null && attemptedUserLogin.checkPassword(request.getParameter("password"))){
            failed = false;
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", attemptedUserLogin);
            if(attemptedUserLogin.isManager()){
                response.sendRedirect("manager");
            } else {
                response.sendRedirect("employee");
            }

        } else {
            failed = true;
            response.sendRedirect("login");


        }
    }
}
