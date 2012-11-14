package eai.msejdf.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import eai.msejdf.config.Configuration;

/**
 * @author dcruz
 * Creates an authenticator class for sending emails through required authenticated smtp hosts
 */
class EmailUtilsAuthenticator extends Authenticator
{
	private PasswordAuthentication authentication;

	public EmailUtilsAuthenticator() {
		String username = Configuration.getSmtpUser();
		String password = Configuration.getSmtpPass();
		authentication = new PasswordAuthentication(username, password);
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
}
