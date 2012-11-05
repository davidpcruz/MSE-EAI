package app.config;

import helper.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.config.IUserConfig;
import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.exception.SecurityException;
import eai.msejdf.persistence.User;

public class TestSetUserInfo {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 */
	public static void main(String[] args) throws NamingException, IOException, SecurityException, ConfigurationException {
		
		InitialContext ctx = new InitialContext();
		IUserConfig bean = (IUserConfig) ctx.lookup(Resource.JNDI_USER_CONFIG_BEAN);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		User userInfo = new User();
		
		System.out.println("Username: ");
		String user = br.readLine();
		
		System.out.println("Name: ");
		userInfo.setName(br.readLine());
		
		System.out.println("Address: ");
		userInfo.getAddress().setAddress(br.readLine());

		System.out.println("Zip: ");
		userInfo.getAddress().setZipCode(br.readLine());
		
		bean.configureUser(user);
		bean.setUserInfo(userInfo);
		bean.saveConfig();
		
		System.out.println("User info set");
	}

}
