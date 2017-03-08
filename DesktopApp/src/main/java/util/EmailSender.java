package util;

import entity.User;
import service.UserService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailSender {
    private static String BODY_NEW_USER(User user) {
        return "Hello, " + user.getLogin() + "!\nYour password: " + user.getPassword();
    };
    private static String BODY_RESET_PASS(User user) {
        return "Hello, " + user.getLogin() + "!\nYou reset your password. Here your new password: " + user.getPassword();
    }
    private static String BODY_NEW_PASS(User user) {
        return "Hello, " + user.getLogin() + "!\nYou changed your password. Your new password: " + user.getPassword();
    }

    private static Boolean send(User user, String body) {
        Properties prop = new Properties();
        InputStream input;

        try {
            input = new FileInputStream(EmailSender.class
                    .getResource("/").getPath() + "mail.properties");
            // load a properties file
            prop.load(input);
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        }

        final String FROM = prop.getProperty("from");
        final String TO = user.getEmployee().getEmail();

        final String BODY = body;
        final String SUBJECT = "Account details";

        final String SMTP_USERNAME = prop.getProperty("username");
        final String SMTP_PASSWORD = prop.getProperty("password");

        final String HOST = prop.getProperty("host");

        final int PORT = 465;

        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.smtps.host", HOST);
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.port", PORT);

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        try {
            // Create a message with the specified information.
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
            msg.setSubject(SUBJECT);
            msg.setContent(BODY, "text/plain");

            // Create a transport.
            Transport transport = session.getTransport();

            // Send the message.
            try {
                System.out.println("Attempting to send an email through the SMTP interface...");
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
                // Send the email.
                transport.sendMessage(msg, msg.getAllRecipients());
                System.out.println("Email sent!");
                return true;
            } catch (Exception ex) {
                System.out.println("The email was not sent.");
                System.out.println("Error message: " + ex.getMessage());
                return false;
            } finally {
                // Close and terminate the connection.
                transport.close();
            }
        } catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
    }

    public static Boolean resetPass(User user) {
        String password = user.getPassword();
        user.setPassword(LoginHelper.generatePassword());
        if (send(user, BODY_RESET_PASS(user))) {
            ApplicationContextFactory.getApplicationContext().getBean(UserService.class).update(user);
            return true;
        } else {
            user.setPassword(password);
            return false;
        }
    }

    public static Boolean newUser(User user) {
        return send(user, BODY_NEW_USER(user));
    }

    // new password must be saved into 'user'
    public static Boolean newPass(User user) {
        if (send(user, BODY_NEW_PASS(user))) {
            ApplicationContextFactory.getApplicationContext().getBean(UserService.class).update(user);
            return true;
        } else {
            // revert changes
            user.setPassword(
                    ApplicationContextFactory.getApplicationContext()
                            .getBean(UserService.class)
                            .read(user.getId()).getPassword());
            return false;
        }
    }
}
