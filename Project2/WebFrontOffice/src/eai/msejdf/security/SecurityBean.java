package eai.msejdf.security;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.NamingManager;

import eai.msejdf.exception.SecurityException;
import eai.msejdf.security.ISecurity;
import eai.msejdf.security.credentials.Credentials;
import eai.msejdf.security.credentials.UserCredentials;

@ManagedBean
@ViewScoped
public class SecurityBean
{
	private ISecurity bean;
	private String username;
	private String password;
	
	public SecurityBean() throws NamingException
	{
		InitialContext ctx = new InitialContext();
	
		// TODO: Remove hardcoded
		bean = (ISecurity) ctx.lookup("ejb:P2EARDeploy/EJBServer/Security!eai.msejdf.security.ISecurity");
	}
	
	public ISecurity accessor()
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
		
		return this.bean.CheckUser(credentials);
	}
}
