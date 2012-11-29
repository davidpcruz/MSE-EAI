package eai.msejdf.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import eai.msejdf.config.Configuration;

public class EmailUtils
{
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	private static final String MAIL_SMTP_HOST = "mail.smtp.host";
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmailUtils.class);

	public static boolean sendEmail(String from, String to, String subject,
			String message)
	{
		// create a new authenticator for the SMTP server
		EmailUtilsAuthenticator authenticator = new EmailUtilsAuthenticator();
		
		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty(MAIL_SMTP_HOST, Configuration.getSmtpHost());
		// setup the authentication
		properties.setProperty(MAIL_SMTP_AUTH, "true");
		
		// Get the Session object with the authenticator.
		Session session = Session.getInstance(properties, authenticator);

		try
		{
			MimeMessage mime = new MimeMessage(session);
			mime.setFrom(new InternetAddress(from));
			mime.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			mime.setSubject(subject);
			mime.setText(message);

			// Send it
			Transport.send(mime);

			return true;

		} catch (MessagingException mex)
		{
			logger.error("sendEmail(String, String, String, String) - exception ", mex); 
			return false;
		}
	}
}
