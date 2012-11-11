package app.user;

import helper.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.exception.SecurityException;
import eai.msejdf.persistence.Address;
import eai.msejdf.persistence.BankTeller;
import eai.msejdf.persistence.User;
import eai.msejdf.user.IUserBean;

public class TestSetUserInfo {

	/**
	 * @param args
	 * @throws ConfigurationException 
	 */
	public static void main(String[] args) throws NamingException, IOException, SecurityException, ConfigurationException {
		
		InitialContext ctx = new InitialContext();
		IUserBean bean = (IUserBean) ctx.lookup(Resource.JNDI_USER_BEAN);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Username: ");
		String username = br.readLine();
		
		User userInfo = bean.getUser(username);
		
		Address address = userInfo.getAddress();
		if (null == address)
		{
			address = new Address();
			userInfo.setAddress(address);
		}				
		BankTeller bankTeller = userInfo.getBankTeller();
		if (null == bankTeller)
		{
			bankTeller = new BankTeller();
			userInfo.setBankTeller(bankTeller);
		}				
						
		System.out.println("Name: ");
		userInfo.setName(br.readLine());
		
		System.out.println("Address: ");
		address.setAddress(br.readLine());

		System.out.println("Zip: ");
		address.setZipCode(br.readLine());
		
		bankTeller.setName("BankTeller Name");
		bankTeller.setPassword("pass");
		
		bean.updateUser(userInfo);
		
		System.out.println("User info set");
	}

}
