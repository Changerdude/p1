package servlet.employee;

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

public class UpdateAccountServlet extends HttpServlet {
    UserDao userDao = UserDaoFactory.getUserDao();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        User user =(User) session.getAttribute("currentUser");
        request.getRequestDispatcher("pageHeader.html").include(request, response);
        out.println("<h2>"+user.getName() + "'s Account</h2><hr />");
        out.println("<form action='employee_update' method='post'>");
        out.println("<div>" +
                "            <label for='email'>Email:</label>" +
                "            <input type='text' name='email' id='email' class='form-control' required='required' value='" + user.getEmail() + "'>" +
                "          </div>" +
                "          <div>" +
                "            <label for='name'>Name:</label>" +
                "            <input type='text' name='name' id='name' class='form-control' required='required' value='" + user.getName() + "'>" +
                "          </div>" +
                "          <div>" +
                "           <label for='password'>Password:</label>"+
                "           <input type='password' name='password' id='password' class='form-control' required='required' value='" + user.getPassword() + "'>" +
                "          <input type='submit' value='Submit' class='btn btn-primary btn-block'>"+
                "          <a href='employee' class='btn btn-danger btn-block' role='button'>Back</a>");
        out.println("</form>");
        request.getRequestDispatcher("pageFooter.html").include(request, response);

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("currentUser");
        user.setEmail(request.getParameter("email"));
        user.setName(request.getParameter("name"));
        user.setPassword(request.getParameter("password"));
        session.setAttribute("currentUser", user);
        userDao.updateUser(user);
        response.sendRedirect("employee");
    }
}
