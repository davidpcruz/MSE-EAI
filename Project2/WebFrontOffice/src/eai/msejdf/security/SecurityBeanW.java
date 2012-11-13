package eai.msejdf.security;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.session.SessionManager;
import eai.msejdf.exception.SecurityException;
import eai.msejdf.security.ISecurity;
import eai.msejdf.security.credentials.Credentials;
import eai.msejdf.security.credentials.UserCredentials;

@ManagedBean
@ViewScoped
public class SecurityBeanW
{
	private ISecurity bean;
	private String username;
	private String password;
	
	public SecurityBeanW() throws NamingException
	{
		InitialContext ctx = new InitialContext();
	
		// TODO: Remove hardcoded
		bean = (ISecurity) ctx.lookup("ejb:P2EARDeploy/EJBServer/Security!eai.msejdf.security.ISecurity");
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

	public boolean checkUserCredentials() throws SecurityException
	{
		Credentials credentials = new UserCredentials();
		
		credentials.setUsername(this.getUsername());
		credentials.setPassword(this.getPassword());
		
		boolean result = this.bean.checkUser(credentials);
		
		if (false != result)
		{
			SessionManager.setProperty(SessionManager.USERNAME_PROPERTY, this.getUsername());
		}
		return result;
	}
}
