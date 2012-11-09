package eai.msejdf.security;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.security.ISecurity;

@ManagedBean
@ViewScoped
public class SecurityBean
{
	private ISecurity bean;
	
	public SecurityBean() throws NamingException
	{
		InitialContext ctx = new InitialContext();
		// TODO: Remove hardcoded
		bean = (ISecurity) ctx.lookup("P2EARDeploy/EJBServer/Security!eai.msejdf.security.ISecurity");		
	}
	
	public ISecurity accessor()
	{
		return this.bean;
	}

}
