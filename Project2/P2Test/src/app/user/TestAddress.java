package app.user;

import helper.Resource;

import java.io.IOException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.exception.SecurityException;
import eai.msejdf.persistence.Address;
import eai.msejdf.persistence.BankTeller;
import eai.msejdf.persistence.User;
import eai.msejdf.user.IUserBean;

public class TestAddress {

	public static void main(String[] args) throws NamingException, IOException,
			SecurityException, ConfigurationException {

		InitialContext ctx = new InitialContext();
		IUserBean bean = (IUserBean) ctx.lookup(Resource.JNDI_USER_BEAN);

		Long userId_1 = (long) 1;
		Long userId_2 = (long) 2;
		Long userId_3 = (long) 3;
		Long userId_not = (long) 9999;
		Long companyId_1 = (long) 1;
		Long companyId_2 = (long) 2;
		Long companyId_3 = (long) 3;
		Long companyId_not = (long) 9999;
		Long tellerId_1 = (long) 1;
		Long tellerId_2 = (long) 2;
		Long tellerId_3 = (long) 3;

		Long tellerId_not = (long) 9999;

//		TestsetBankTeller(bean, userId_2, tellerId_1);
//		TestsetBankTellerAddress(bean, userId_3);
		TestgetEagerBankTellerList(bean, "%");

	}

	private static void TestgetEagerBankTellerList(IUserBean bean, 
			String fildPattern) throws ConfigurationException {
		List<BankTeller> bankTellerList = bean.getEagerBankTellerList(fildPattern);
		for( BankTeller bankTeller : bankTellerList ){
			System.out.println(bankTeller.getName()+ " " + bankTeller.getAddress());
		}
	}
	
	private static void TestsetBankTellerAddress(IUserBean bean, 
			Long userId) throws ConfigurationException {
		
		Address address = new Address();
		address.setCity("Viana Do Castelo");
		address.setAddress("rua das flores");
		address.setZipCode("4900");
		
		System.out.println(address);

		User user =bean.getUser(userId);
		System.out.println(user);
		
		
		BankTeller bankTeller = bean.getUserBankTeller(user);
				System.out.println(bankTeller);
		
		bankTeller.setAddress(address);
		System.out.println("Actual backTeller is: "
				+ bean.getUserBankTeller(user));
		
//		bean.setBankTellerAddress(bankTeller);
		
		bean.setBankTeller(userId,bankTeller);
		System.out.println("Actual backTeller is: "
				+ bean.getUser(userId).getName() + " "
				+ bean.getUserBankTeller(user));
		
	
	}
	
	private static void TestsetBankTeller(IUserBean bean, Long userId,
			Long tellerId) throws ConfigurationException {

		System.out
				.println("Testing  setBankTeller(user, BankTeller,tellerId);");
		System.out.println("set BankTeller User/BankTeller " + userId + " "
				+ tellerId);
		bean.setBankTeller(userId, tellerId);
		System.out.println("Actual backTeller is: "
				+ bean.getUser(userId).getName() + " "
				+ bean.getBankTeller(userId));
	}

	/**
	 * @param bean
	 * @param userId
	 * @param tellerName
	 * @throws ConfigurationException
	 */
	private static void TestsetBankTeller(IUserBean bean, Long userId,
			String tellerName) throws ConfigurationException {
		// bankTeller.setId( (long) 1);

		tellerName = "qwerty4";
		System.out.println("Testing  setBankTeller(user, BankTeller)");
		BankTeller bankTeller = null;
		List<String> bankTellers = bean.getBankTellerNameList("%");

		for (String b : bankTellers)
			System.out.println("\t\t tellerName : " + b);

		if (bankTellers.contains(tellerName)) {

			bankTeller = bean.getBankTellerList(tellerName).get(0);

			// bankTeller.setName("CGD2_2");
			System.out.println("tellerName already exists"
					+ bankTeller.toString());

			bean.setBankTeller(userId, bankTeller);

			System.out.println("set BankTeller User/BankTeller " + userId + " "
					+ bankTeller.getId());

		} else {
			System.out.println("creating new bankTeller: " + tellerName);
			bankTeller = new BankTeller();
			bankTeller.setName(tellerName);

			System.out.println("set BankTeller User/BankTeller " + userId + " "
					+ bankTeller.getId());
			bean.setBankTeller(userId, bankTeller);
		}

	}
}
