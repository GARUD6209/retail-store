package com.amstech.retail.test;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class MailTest {
    public static void main(String[] args) {
        final String username = "jventure360@gmail.com";
        final String password = "Ika@4928";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("no-reply@example.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("santoshpatel4949@gmail.com"));
            message.setSubject("Test Mail");
            message.setText("This is a test mail.");

            Transport.send(message);
            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
