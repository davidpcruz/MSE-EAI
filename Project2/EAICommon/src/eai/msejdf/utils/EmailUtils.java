package eai.msejdf.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils
{
	public static boolean sendEmail(String from, String to, String subject, String message ) {
		
	      // Assuming you are sending email from localhost
	      String host = "localhost";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage mime = new MimeMessage(session);

	         // Set From: header field of the header.
	         mime.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         mime.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         // Set Subject: header field
	         mime.setSubject(subject);

	         // Now set the actual message
	         mime.setText(message);

	         // Send message
	         Transport.send(mime);

	         return true;
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	         return false;
	      }
	}
}
