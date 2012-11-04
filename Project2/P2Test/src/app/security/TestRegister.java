package app.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.SecurityException;
import eai.msejdf.security.ISecurity;

public class TestRegister {

	/**
	 * @param args
	 * @throws NamingException 
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws NamingException, IOException, SecurityException {
		
		InitialContext ctx = new InitialContext();
		ISecurity bean = (ISecurity) ctx.lookup("");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Username: ");
		String user = br.readLine();
		
		System.out.println("Password: ");
		String pass = br.readLine();
		
		bean.RegisterUser(user, pass);
	}

}
