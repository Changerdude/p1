package servlet.manager;

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
import java.util.Properties;
import java.util.Random;

public class ManagerNewAccountServlet extends HttpServlet {
    UserDao userDao = UserDaoFactory.getUserDao();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("managerSignup.html").include(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);

        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setName(request.getParameter("name"));
        String isManager = request.getParameter("isManager");
        user.setManager(isManager.equals("true"));
        String generatedPass = randomString();
        user.setPassword(generatedPass);
        userDao.saveUser(user);
        emailNewUser((User) session.getAttribute("currentUser"), user, generatedPass);
        response.sendRedirect("manager");
    }

    private void emailNewUser(User manager, User newUser, String generatedPass){

        String to = newUser.getEmail();
        String from = manager.getEmail();
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
            message.setSubject("Your expense account has been created");
            //set the content of the email message
            message.setText("This email is to inform you that your new account was set up. Use this email address and the password: " +
                    generatedPass + " to login. Please change this password to something you can remember.");
            //send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public String randomString(){

            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();

            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            return generatedString;
    }

}
