package servlet.employee;

import entity.expenseRequest.ExpenseDao;
import entity.expenseRequest.ExpenseDaoFactory;
import entity.expenseRequest.ExpenseRequest;
import entity.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;

public class SubmitExpenseServlet extends HttpServlet {
    private ExpenseDao expenseDao = ExpenseDaoFactory.getExpenseDao();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("newExpense.html").include(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setOwner(currentUser);
        expenseRequest.setSubmitId(currentUser.getId());
        expenseRequest.setName(request.getParameter("name"));
        expenseRequest.setDescription(request.getParameter("description"));
        expenseRequest.setAmount(Float.valueOf(request.getParameter("amount")));
        expenseRequest.setStatus("Pending");
        expenseRequest.setDate(Timestamp.from(Instant.now()));
        expenseDao.saveExpense(expenseRequest);
        response.sendRedirect("employee");
    }
}
