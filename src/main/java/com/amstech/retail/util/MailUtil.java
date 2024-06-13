package com.amstech.retail.util;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class MailUtil {
    private final String SMTP_HOST;
    private final String SMTP_PORT;
    private final String SMTP_AUTH_USER;
    private final String SMTP_AUTH_PASSWORD;

    public MailUtil(String smtpHost, String smtpPort, String smtpAuthUser, String smtpAuthPassword) {
        this.SMTP_HOST = smtpHost;
        this.SMTP_PORT = smtpPort;
        this.SMTP_AUTH_USER = smtpAuthUser;
        this.SMTP_AUTH_PASSWORD = smtpAuthPassword;
    }

    public void sendEmail(String toEmail, String subject, String messageContent) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_AUTH_USER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(messageContent);

        Transport.send(message);
    }
}
