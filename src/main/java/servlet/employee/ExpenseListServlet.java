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
import java.util.List;

public class ExpenseListServlet extends HttpServlet {
    ExpenseDao expenseDao = ExpenseDaoFactory.getExpenseDao();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("currentUser");
        List<ExpenseRequest> expenseRequests = expenseDao.getExpensesByUser(user.getId());
        request.getRequestDispatcher("pageHeader.html").include(request, response);
        out.println("<h2>" + user.getName() +"'s Expenses</h2><hr />");
        out.println("<input class='form-control' id='myInput' type='text' placeholder='Search..'>");
        out.println("<table class='table table-bordered'>");
        out.println("<thead><tr><th>For</th><th>Description</th><th>Amount</th><th>Status</th><th>Time submitted</th></tr></thead>");
        out.println("<tbody id='myTable'>");
        for(ExpenseRequest expenseRequest: expenseRequests){
            out.println("<tr>");
            out.println("<td>" + expenseRequest.getName() + "</td>");
            out.println("<td>" + expenseRequest.getDescription() + "</td>");
            out.println("<td>$" + expenseRequest.getAmount() + "</td>");
            out.println("<td>" + expenseRequest.getStatus() + "</td>");
            out.println("<td>" + expenseRequest.getDate() + "</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("<a href='employee' class='btn btn-primary btn-block' role='button'>Back</a>");

        request.getRequestDispatcher("pageFooterWithSearchScript.html").include(request, response);
    }

}
