package app.security;

import helper.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.SecurityException;
import eai.msejdf.security.credentials.Credentials;
import eai.msejdf.security.ISecurity;
import eai.msejdf.security.credentials.AdminCredentials;
import eai.msejdf.security.credentials.UserCredentials;

public class TestRegister {

	/**
	 * @param args
	 * @throws NamingException 
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws NamingException, IOException, SecurityException {
		
		Credentials credentials;
		InitialContext ctx = new InitialContext();
		ISecurity bean = (ISecurity) ctx.lookup(Resource.JNDI_SECURIY_BEAN);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Username: ");
		String user = br.readLine();
		
		System.out.println("Password: ");
		String pass = br.readLine();
		
		System.out.println("User type (u=user; a=admin)");
		String type = br.readLine();
		
		if (type.equals("a"))
		{
			credentials = new AdminCredentials();
		}
		else
		{
			// Assume user as default
			credentials = new UserCredentials();
		}
		credentials.setUsername(user);
		credentials.setPassword(pass);
		
		bean.registerUser(credentials);
		
		System.out.println("User registered");
	}

}
