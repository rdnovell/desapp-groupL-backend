package ar.edu.unq.grupol.app.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    final String username = "ciu.dominos.pizza@gmail.com";
    final String password = "interfaces2017";

	public static EmailSender instance = new EmailSender();

	public EmailSender() {

	}

	public static EmailSender getInstance() {
		if (instance == null) {
			instance = new EmailSender();
		}
		return instance;
	}

	public void send(String titulo, String email) throws SendFailedException {
		try {
			MimeMessage message = new MimeMessage(createSession());
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(titulo);
			message.setText("Invitation");
			Transport.send(message);
		} catch (MessagingException e) {
			//e.printStackTrace();
			throw new SendFailedException("Invalid send address", e);
		}
	}

	private Session createSession() {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");

		Authenticator authentication = new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }};
		return Session.getInstance(prop, authentication);
	}
}
