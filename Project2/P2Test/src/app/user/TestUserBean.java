package app.user;

import helper.Resource;

import java.io.IOException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.exception.SecurityException;
import eai.msejdf.persistence.BankTeller;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;
import eai.msejdf.user.IUserBean;

public class TestUserBean {

	/**
	 * @param args
	 * @throws NamingException
	 * @throws IOException
	 * @throws SecurityException
	 * @throws ConfigurationException
	 */

	public static void main(String[] args) throws NamingException, IOException,
			SecurityException, ConfigurationException {

		InitialContext ctx = new InitialContext();
		IUserBean bean = (IUserBean) ctx.lookup(Resource.JNDI_USER_BEAN);

		Long userId_1 = (long) 1;
		Long userId_2 = (long) 2;
		Long userId_3 = (long) 3;
		Long userId_not = (long) 2;
		Long companyId_1 = (long) 1;
		Long companyId_2 = (long) 2;
		Long companyId_3 = (long) 3;
		Long companyId_not = (long) 2;
		Long tellerId_1 = (long) 1;
		Long tellerId_2 = (long) 2;
		Long tellerId_3 = (long) 3;

		Long tellerId_not = (long) 9999;

		TestgetCompany(bean, companyId_1);

		TestgetCompany(bean, companyId_not);

		TestgetUser(bean, "test");

		TestgetCompanyList(bean, "%C%");

		System.out
				.println("Testing  TestgetCompanyList with an invalide company pattern");
		TestgetCompanyList(bean, "sadgasdgasfhahasfh");

		TestgetCompanyList(bean, "%C%");

		TestgetCompanyNameList(bean, "%C%");

		// Follow companies
		TestfollowCompany(bean, userId_1, companyId_1);
		TestfollowCompany(bean, userId_1, companyId_2);
		TestfollowCompany(bean, userId_1, companyId_2);
		TestfollowCompany(bean, userId_1, companyId_3);
		TestfollowCompany(bean, userId_2, companyId_1);
		TestfollowCompany(bean, userId_2, companyId_2);
		TestgetfollowedCompanyList(bean, companyId_1);

		TestunfollowCompany(bean, userId_1, companyId_2);
		TestgetfollowedCompanyList(bean, userId_1);


		TestsetBankTeller(bean, userId_1, "vvv6");
		TestsetBankTeller(bean, userId_2, "vvv5");
		
//		TestsetBankTeller(bean, userId_2, "BES");
//		TestsetBankTeller(bean, userId_1, tellerId_2);
//		TestgetBankTeller(bean, tellerId_1);
//		TestgetBankTellerList(bean, "%");
//		TestgetBankTellerNameList(bean, "%");

		ctx.close();
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

		// tellerName = "qwerty4";
		System.out.println("Testing  setBankTeller(user, BankTeller)");
		BankTeller bankTeller = new BankTeller();
//		List<String> bankTellers = bean.getBankTellerNameList("%");
//
//		for (String b : bankTellers)
//			System.out.println("\t\t tellerName : " + b);
//
//		if (bankTellers.contains(tellerName)) {
//
//			bankTeller = bean.getBankTellerList(tellerName).get(0);
//			// bankTeller.setPassword("A");
			 bankTeller.setName(tellerName);
//			System.out.println("tellerName already exists"
//					+ bankTeller.toString());

			bean.setBankTeller(userId, bankTeller);

			System.out.println("set BankTeller User/BankTeller " + userId + " "
					+ bankTeller.getId());

//		} else {
//			System.out.println("creating new bankTeller: " + tellerName);
//			bankTeller = new BankTeller();
//			bankTeller.setName(tellerName);
//			// bankTeller.setPassword("asasd");
//			System.out.println("set BankTeller User/BankTeller " + userId + " "
//					+ bankTeller.getId());
//			bean.setBankTeller(userId, bankTeller);
//		}

	}

	/**
	 * @param bean
	 * @param userId
	 * @param tellerId
	 * @throws ConfigurationException
	 */
	private static void TestsetBankTeller(IUserBean bean, Long userId,
			Long tellerId) throws ConfigurationException {

		System.out
				.println("Testing  setBankTeller(user, BankTeller,tellerId);");
		System.out.println("set BankTeller User/BankTeller " + userId + " "
				+ tellerId);
		bean.setBankTeller(userId, tellerId);
	}

	/**
	 * @param bean
	 * @param bankTellerId
	 * @throws ConfigurationException
	 */
	private static void TestgetBankTeller(IUserBean bean, Long bankTellerId)
			throws ConfigurationException {

		System.out.println("Testing  getBankTeller(companyName)");
		System.out.println("Searching Bank Teller Name: " + bankTellerId);
		BankTeller bankTeller = bean.getBankTeller(bankTellerId);
		System.out.println("\t Result " + bankTeller.getName());
	}

	/**
	 * @param bean
	 * @param filterPattern
	 * @throws ConfigurationException
	 */
	private static void TestgetBankTellerList(IUserBean bean,
			String filterPattern) throws ConfigurationException {

		System.out.println("Testing  getBankTellerList(filterPattern)");
		System.out.println("Searching BankTeller Name: " + filterPattern);
		List<BankTeller> bankTellers = bean.getBankTellerList(filterPattern);
		for (BankTeller bankTeller : bankTellers)
			System.out.println("\t Result " + bankTeller.getName());
	}

	/**
	 * @param bean
	 * @param filterPattern
	 * @throws ConfigurationException
	 */
	private static void TestgetBankTellerNameList(IUserBean bean,
			String filterPattern) throws ConfigurationException {

		System.out.println("Testing  getBankTellerNameList(filterPattern)");
		System.out.println("Searching BankTeller Name: " + filterPattern);
		List<String> company = bean.getBankTellerNameList(filterPattern);
		for (String comp : company)
			System.out.println("\t Result " + comp);
	}

	/**
	 * @param bean
	 * @param userId
	 * @throws ConfigurationException
	 */
	private static void TestgetfollowedCompanyList(IUserBean bean, Long userId)
			throws ConfigurationException {

		System.out.println("Testing  getfollowedCompanyList(filterPattern)");
		List<Company> companies = bean.getfollowedCompanyList(userId);

		// System.out.println("\t Result " + companies);
		for (Company company : companies)
			System.out.println("\t Result " + company.getName());
	}

	/**
	 * @param bean
	 * @param userId
	 * @param companyId
	 * @throws ConfigurationException
	 */
	private static void TestfollowCompany(IUserBean bean, Long userId,
			Long companyId) throws ConfigurationException {

		System.out.println("Testing  followCompany(user, company);");
		System.out.println("Adding following Company User/Company " + userId
				+ " " + companyId);
		bean.followCompany(userId, companyId);
	}

	/**
	 * @param bean
	 * @param userId
	 * @param companyId
	 * @throws ConfigurationException
	 */
	private static void TestunfollowCompany(IUserBean bean, Long userId,
			Long companyId) throws ConfigurationException {

		System.out.println("Testing  unfollowCompany(user, company)");
		System.out.println("Adding following Company User/Company " + userId
				+ " " + companyId);
		bean.unfollowCompany(userId, companyId);
	}

	/**
	 * @param bean
	 * @param companyId
	 * @throws ConfigurationException
	 */
	private static void TestgetCompany(IUserBean bean, Long companyId)
			throws ConfigurationException {

		System.out.println("Testing  getCompany(companyName)");
		System.out.println("Searching Company Name: " + companyId);
		Company company = bean.getCompany(companyId);
		System.out.println("\t Result " + company.getName());
	}

	/**
	 * @param bean
	 * @param filterPattern
	 * @throws ConfigurationException
	 */
	private static void TestgetCompanyList(IUserBean bean, String filterPattern)
			throws ConfigurationException {

		System.out.println("Testing  getCompanyList(filterPattern)");
		System.out.println("Searching Company Name: " + filterPattern);
		List<Company> company = bean.getCompanyList(filterPattern);
		for (Company comp : company)
			System.out.println("\t Result " + comp.getName());
	}

	/**
	 * @param bean
	 * @param filterPattern
	 * @throws ConfigurationException
	 */
	private static void TestgetCompanyNameList(IUserBean bean,
			String filterPattern) throws ConfigurationException {

		System.out.println("Testing  getCompanyNameList(filterPattern)");
		System.out.println("Searching Company Name: " + filterPattern);
		List<String> company = bean.getCompanyNameList(filterPattern);
		for (String comp : company)
			System.out.println("\t Result " + comp);
	}

	/**
	 * @param bean
	 * @param filterPattern
	 * @throws ConfigurationException
	 */
	private static void TestgetUser(IUserBean bean, String filterPattern)
			throws ConfigurationException {

		System.out.println("Testing  getName(user)");
		System.out.println("Searching Company Name: " + filterPattern);
		User company = bean.getUser(filterPattern);
		System.out.println("\t Result " + company.getName());
	}

}
