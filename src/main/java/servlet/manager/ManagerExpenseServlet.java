package servlet.manager;

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

public class ManagerExpenseServlet extends HttpServlet {
    ExpenseDao expenseDao = ExpenseDaoFactory.getExpenseDao();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        ExpenseRequest currentExpense = expenseDao.getExpenseById(Integer.parseInt(request.getParameter("id")));

        request.getRequestDispatcher("pageHeader.html").include(request, response);
        out.println("<h2>Expense from " + currentExpense.getOwner().getName() + ".</h2>");
        out.println("<table class='table table-bordered'>");
        out.println("<thead><tr><th>For</th><th>Description</th<<th>Amount</th><th>Status</th><th>Time submitted</th></tr></thead>");
        out.println("<tbody id='myTable'>");
        out.println("<tr>");
        out.println("<td>" + currentExpense.getName() + "</td>");
        out.println("<td>" + currentExpense.getDescription() + "</td>");
        out.println("<td>$" + currentExpense.getAmount() + "</td>");
        out.println("<td>" + currentExpense.getStatus() + "</td>");
        out.println("<td>" + currentExpense.getDate() + "</td>");
        out.println("</tr>");
        out.println("</tbody>");
        out.println("</table>");
        out.println("<a href='manager?id="+currentExpense.getId()+"&status=Approved' class='btn btn-success btn-block' role='button'>Approve</a>");
        out.println("<a href='manager?id="+currentExpense.getId()+"&status=Denied' class='btn btn-danger btn-block' role='button'>Deny</a>");
        out.println("<a href='manager' class='btn btn-primary btn-block' role='button'>Back</a>");
        request.getRequestDispatcher("pageFooter.html");
    }
}
