package eai.msejdf.pagedata;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.SecurityException;
import eai.msejdf.security.ISecurity;
import eai.msejdf.security.credentials.Credentials;
import eai.msejdf.security.credentials.UserCredentials;
import eai.msejdf.utils.EJBLookupConstants;
import eai.msejdf.web.session.SessionManager;

public class SecurityPageData {
	
	private ISecurity securityBean;
	private String username;
	private String password;
	
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
}
