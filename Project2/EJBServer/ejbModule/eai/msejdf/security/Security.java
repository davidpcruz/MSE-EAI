package eai.msejdf.security;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import eai.msejdf.security.ISecurity;


/**
 * Bean implementing interface for security related calls, such as login, registration, etc.
 */
@Stateless
@LocalBean
public class Security implements ISecurity{

	public void RegisterUser(String username, String password) {
		
	}

	public void Login(String username, String password) {
		
	}

	public void Logout(String username)
	{
		
	}
}
