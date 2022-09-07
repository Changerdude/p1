package servlet.manager;

import entity.expenseRequest.ExpenseDao;
import entity.expenseRequest.ExpenseDaoFactory;
import entity.expenseRequest.ExpenseRequest;
import entity.user.User;
import entity.user.UserDao;
import entity.user.UserDaoFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

public class ManagerHomePageServlet extends HttpServlet {
    ExpenseDao expenseDao = ExpenseDaoFactory.getExpenseDao();
    UserDao userDao = UserDaoFactory.getUserDao();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");

        String status = request.getParameter("status");

        if(status != null){
            if(request.getParameter("id") != null){
                int incomingId = Integer.parseInt(request.getParameter("id"));
                ExpenseRequest requestToUpdate = expenseDao.getExpenseById(incomingId);
                requestToUpdate.setStatus(status);
                expenseDao.updateExpenseRequest(requestToUpdate);
                emailStatusChange(requestToUpdate, currentUser);
            }
        }
        List<ExpenseRequest> expenseRequests = expenseDao.getAllExpenses();
        List<User> users = userDao.getUsersByPosition(false);

        request.getRequestDispatcher("managerTabListHeader.html").include(request, response);
        out.println("<h2>Welcome, " + currentUser.getName() + "!");


        //Take expenses and show on searchable table

        out.println("<h2>Expense Requests</h2>");
        out.println("<input class='form-control' id='myInput' type='text' placeholder='Search..'>");
        out.println("<table class='table table-bordered table-hover'>");
        out.println("<thead><tr><th>Submitted By</th><th>For</th><th>Amount</th><th>Status</th><th>Time submitted</th></tr></thead>");
        out.println("<tbody id='myTable'>");
        for(ExpenseRequest expenseRequest: expenseRequests){
            out.println("<tr class='table-row' data-href='manager_expense?id="+expenseRequest.getId()+"'>");
            out.println("<td>" + expenseRequest.getOwner().getName() + "</td>");
            out.println("<td>" + expenseRequest.getName() + "</td>");
            out.println("<td>$" + expenseRequest.getAmount() + "</td>");
            out.println("<td>" + expenseRequest.getStatus() + "</td>");
            out.println("<td>" + expenseRequest.getDate() + "</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");

        out.println("<h2>Employees</h2>");
        out.println("<table class='table table-bordered'>");
        out.println("<thead><tr><th>Id</th><th>Name</th><th>Email</th></tr></thead>");
        out.println("<tbody>");
        for(User user: users){
            out.println("<tr>");
            out.println("<td>" + user.getId() + "</td>");
            out.println("<td>" + user.getName() + "</td>");
            out.println("<td>" + user.getEmail() + "</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("<a href='manager_signup' class='btn btn-primary btn-block' role='button'>Add New Employee</a>");
        out.println("<a href='logout' class='btn btn-primary btn-block' role='button'>Logout</a>");
        request.getRequestDispatcher("pageFooterWithSearchScript.html").include(request, response);
    }
    private void emailStatusChange(ExpenseRequest changedRequest, User sendingManager){
        String to = changedRequest.getOwner().getEmail();
        String from = sendingManager.getEmail();
        final String username ="7d616676e4b090";
        final String pass ="94eb69db1e1c9c";
        String host = "smtp.mailtrap.io";
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, pass);
                    }
                });
        try {
            //create a MimeMessage object
            Message message = new MimeMessage(session);
            //set From email field
            message.setFrom(new InternetAddress(from));
            //set To email field
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            //set email subject field
            message.setSubject("Your expense request has been " + changedRequest.getStatus() + "");
            //set the content of the email message
            message.setText("This email is to inform you that " + sendingManager.getName() + " has modified your expense reimbursement request. " +
                    changedRequest.getName() + " for $" + changedRequest.getAmount() + " has been: " + changedRequest.getStatus() + ".");
            //send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
