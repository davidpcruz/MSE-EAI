package eai.msejdf.web.backoffice.security;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.SecurityException;
import eai.msejdf.security.ISecurity;
import eai.msejdf.security.credentials.AdminCredentials;
import eai.msejdf.security.credentials.Credentials;
import eai.msejdf.utils.EJBLookupConstants;
import eai.msejdf.web.session.SessionManager;

@ManagedBean(name="securityBeanW")
@ViewScoped
public class SecurityBackOfficeBean
{
	private ISecurity bean;
	private String username;
	private String password;
	
	/**
	 * Hnaldes the login of the back office users
	 * @throws NamingException
	 */
	public SecurityBackOfficeBean() throws NamingException
	{
		InitialContext ctx = new InitialContext();
	
		bean = (ISecurity) ctx.lookup(EJBLookupConstants.EJB_I_SECURITY);
	}
	
	public ISecurity bean()
	{
		return this.bean;
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

	/**
	 * Checks the user credentials
	 * @return
	 * @throws SecurityException
	 */
	public boolean checkUserCredentials() throws SecurityException
	{
		// creates a admin credentials class fro login in the back office
		Credentials credentials = new AdminCredentials();
		
		credentials.setUsername(this.getUsername());
		credentials.setPassword(this.getPassword());
		
		boolean result = this.bean.checkUser(credentials);
		
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
		return true;
	}
}
