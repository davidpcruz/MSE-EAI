package app.user;

import helper.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.exception.SecurityException;
import eai.msejdf.persistence.User;
import eai.msejdf.user.IUserBean;

public class TestGetUserInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws NamingException, IOException, SecurityException, ConfigurationException {
		
		InitialContext ctx = new InitialContext();
		IUserBean bean = (IUserBean) ctx.lookup(Resource.JNDI_USER_BEAN);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Username: ");
		String username = br.readLine();
		
		User user = bean.getUser(username);
		
		System.out.println("User info:");
		System.out.println(user.toString());
	}
}
