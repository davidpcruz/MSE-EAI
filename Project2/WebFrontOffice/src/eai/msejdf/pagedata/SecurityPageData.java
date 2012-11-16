package eai.msejdf.pagedata;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.SecurityException;
import eai.msejdf.pages.RegisterUser;
import eai.msejdf.persistence.User;
import eai.msejdf.security.ISecurity;
import eai.msejdf.security.credentials.Credentials;
import eai.msejdf.security.credentials.UserCredentials;
import eai.msejdf.utils.EJBLookupConstants;
import eai.msejdf.web.session.SessionManager;

public class SecurityPageData {
	
	protected ISecurity securityBean;
	private String username;
	private String password;
	private String newPassword;
	private String oldPassword;
	private String confirmedPassword;
	private User userRegistrationInfo;
	
	public SecurityPageData() throws NamingException {		
		InitialContext ctx = new InitialContext();
		
		this.securityBean = (ISecurity) ctx.lookup(EJBLookupConstants.EJB_I_SECURITY);		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	public String getNewPassword()
	{
		return newPassword;
	}


	public void setNewPassword(String currentPassword)
	{
		this.newPassword = currentPassword;
	}

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
	

	public boolean register()
	{
		Credentials credentials = new UserCredentials();
		
		credentials.setUsername(this.getUsername());
		credentials.setPassword(this.getPassword());
		
		try {
			this.securityBean.registerUser(credentials, this.userRegistrationInfo);
			SessionManager.setProperty(SessionManager.USERNAME_PROPERTY, this.getUsername());
		} catch (EJBException | SecurityException exception) {
			return false;
		}
		return true;
	}

	public User getUserRegistrationInfo() {
		return userRegistrationInfo;
	}

	public void setUserRegistrationInfo(User userRegistrationInfo) {
		this.userRegistrationInfo = userRegistrationInfo;
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


	public User getUserRegistrationInfo() {
		return userRegistrationInfo;
	}

	public void setUserRegistrationInfo(User userRegistrationInfo) {
		this.userRegistrationInfo = userRegistrationInfo;
	}

}
