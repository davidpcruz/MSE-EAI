/*
 * 
 */
package eai.msejdf.pagedata;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.SecurityException;
import eai.msejdf.persistence.User;
import eai.msejdf.security.ISecurity;
import eai.msejdf.security.credentials.Credentials;
import eai.msejdf.security.credentials.UserCredentials;
import eai.msejdf.utils.EJBLookupConstants;
import eai.msejdf.web.SessionManager;

public class SecurityPageData {
	
	protected ISecurity securityBean;
	private String username;
	private String password;
	private String newPassword;
	private String confirmedPassword;
	private User userRegistrationInfo;
	
	public SecurityPageData() throws NamingException {		
		InitialContext ctx = new InitialContext();
		
		this.securityBean = (ISecurity) ctx.lookup(EJBLookupConstants.EJB_I_SECURITY);		
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the confirmed password.
	 *
	 * @return the confirmed password
	 */
	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	/**
	 * Sets the confirmed password.
	 *
	 * @param confirmedPassword the new confirmed password
	 */
	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	/**
	 * Gets the new password.
	 *
	 * @return the new password
	 */
	public String getNewPassword()
	{
		return newPassword;
	}


	/**
	 * Sets the new password.
	 *
	 * @param currentPassword the new new password
	 */
	public void setNewPassword(String currentPassword)
	{
		this.newPassword = currentPassword;
	}

	/**
	 * Check user credentials.
	 *
	 * @return true, if successful
	 * @throws SecurityException the security exception
	 */
	public boolean checkUserCredentials() throws SecurityException
	{
		Credentials credentials = new UserCredentials();
		
		credentials.setUsername(this.getUsername());
		credentials.setPassword(this.getPassword());
		
		boolean result = this.securityBean.checkUser(credentials);
		
		if (false != result)
		{
			SessionManager.setProperty(SessionManager.USERNAME_PROPERTY, this.getUsername());
		}
		return result;
	}
	
	/**
	 * Logout then user from the system.
	 *
	 * @return true, if successful
	 */
	public boolean logout()
	{
		SessionManager.removeProperty(SessionManager.USERNAME_PROPERTY);
		SessionManager.invalidateSession();
		return true;
	}
	
	/**
	 * Change password of the user
	 *
	 * @return true, if successful
	 * @throws SecurityException 
	 */
	public boolean changePassword() throws SecurityException
	{
		// first check the original password is right
		Credentials credentials = new UserCredentials();
		
		credentials.setUsername(SessionManager.getProperty(SessionManager.USERNAME_PROPERTY));
		credentials.setPassword(this.getPassword());
		
		boolean result = this.securityBean.checkUser(credentials);
				
		if (false != result)
		{
			credentials.setPassword(this.getNewPassword()); // the new password
			this.securityBean.updateUserCredentials(credentials);
		}
				
		return result;
	}	
	

	/**
	 * Register a new user in the system.
	 *
	 * @return true, if successful
	 */
	public boolean register()
	{
		Credentials credentials = new UserCredentials();
		
		credentials.setUsername(this.getUsername());
		credentials.setPassword(this.getNewPassword());
		
		try {
			this.securityBean.registerUser(credentials, this.userRegistrationInfo);
			SessionManager.setProperty(SessionManager.USERNAME_PROPERTY, this.getUsername());
		} catch (EJBException | SecurityException exception) {
			return false;
		}
		return true;
	}

	/**
	 * Delete account.
	 *
	 * @return true, if successful
	 * @throws SecurityException 
	 */
	public boolean deleteAccount() throws SecurityException
	{		
		// first check the original password is right
		Credentials credentials = new UserCredentials();
		
		credentials.setUsername(SessionManager.getProperty(SessionManager.USERNAME_PROPERTY));

		boolean result = this.securityBean.removeUser(credentials);
		
		if (false != result)
		{
			// logout
			result = this.logout();
		}
				
		return result;
	}	

	/**
	 * Gets the user registration info.
	 *
	 * @return the user registration info
	 */
	public User getUserRegistrationInfo() {
		return userRegistrationInfo;
	}

	/**
	 * Sets the user registration info.
	 *
	 * @param userRegistrationInfo the new user registration info
	 */
	public void setUserRegistrationInfo(User userRegistrationInfo) {
		this.userRegistrationInfo = userRegistrationInfo;
	}
}
