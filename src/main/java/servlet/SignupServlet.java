package servlet;

import entity.user.User;
import entity.user.UserDao;
import entity.user.UserDaoFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignupServlet extends HttpServlet {
    UserDao userDao = UserDaoFactory.getUserDao();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    request.getRequestDispatcher("signup.html").include(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");

    User user = new User();
    user.setEmail(request.getParameter("email"));
    user.setPassword(request.getParameter("password"));
    user.setName(request.getParameter("name"));
    String isManager = request.getParameter("isManager");
    user.setManager(isManager.equals("true"));
    userDao.saveUser(user);
    response.sendRedirect("login");
    }
}
