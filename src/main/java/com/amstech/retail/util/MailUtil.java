package com.amstech.retail.util;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class MailUtil {
	
	String smtpHost = "smtp.host.com"; // Replace with your SMTP host
	String smtpPort = "587"; // Replace with your SMTP port
	String smtpAuthUser = "yourEmail@host.com"; // Replace with your email
	String smtpAuthPassword = "yourPassword"; // Replace with your email password

       

    public void sendEmail(String toEmail, String subject, String messageContent) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpAuthUser, smtpAuthPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(smtpAuthUser));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(messageContent);

        Transport.send(message);
    }
}
