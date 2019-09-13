package com.tax.verify.mailSender;

import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class EmailSender {
    public static final EmailHostConfig gmail_config = new EmailHostConfig("smtp.gmail.com","587");

    private Properties properties;
    private Session session;
    private InternetAddress fromAddress;

    public EmailSender(EmailHostConfig emailHostConfig, ImmutablePair<String,String> userNamePassword) throws AddressException{
        createProperties(emailHostConfig);
        createSession(userNamePassword);
        createFromAddress(userNamePassword);
    }
    private void createFromAddress(ImmutablePair<String, String> userNamePassword) throws AddressException {
        fromAddress = new InternetAddress(userNamePassword.left);
    }
    private void createSession(final ImmutablePair<String, String> userNamePassword){
        session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userNamePassword.left, userNamePassword.right);
            }
        });

    }

    private void createProperties(EmailHostConfig emailHostConfig){
        properties = new Properties();
        properties.put("mail.smtp.host", emailHostConfig.getHost());
        properties.put("mail.smtp.port", emailHostConfig.getPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        /*
        properties.put("mail.smtp.host", "212.58.3.83");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.socketFactory.fallback", false);
        properties = new Properties();
       */

    }

    public void sendEmail(String toAddress, String subject, String message) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(fromAddress);
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setContent(message, "text/html; charset=UTF-8");

        Transport.send(msg);
    }
}
