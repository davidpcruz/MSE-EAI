package app.security;

import helper.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.SecurityException;
import eai.msejdf.security.ISecurity;

public class TestLogin {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws NamingException, IOException, SecurityException {
		
		InitialContext ctx = new InitialContext();
		ISecurity bean = (ISecurity) ctx.lookup(Resource.JNDI_SECURIY_BEAN);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Username: ");
		String user = br.readLine();
		
		System.out.println("Password: ");
		String pass = br.readLine();
		
		bean.Login(user, pass);
		
		System.out.println("User logged in");
	}

}
