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

	public static void main(String[] args) throws NamingException, IOException,
			SecurityException, ConfigurationException {

		InitialContext ctx = new InitialContext();
		IUserBean bean = (IUserBean) ctx.lookup(Resource.JNDI_USER_BEAN);

		Long userId = (long) 1;
		Long userId_2 = (long) 2;
		Long companyId_1 = (long) 1;
		Long companyId_2 = (long) 2;
		Long tellerId_1 = (long) 1;
		Long tellerId_2 = (long) 2;
		
		TestgetCompany(bean, companyId_1);
		TestgetUser(bean, "test");

		TestgetCompanyList(bean, "%C%");
		TestgetCompanyNameList(bean, "%C%");
		TestfollowCompany(bean, userId, companyId_1);
		TestfollowCompany(bean, userId, companyId_2);
		TestgetfollowedCompanyList(bean, companyId_1);
		TestunfollowCompany(bean, userId, companyId_2);
		TestgetfollowedCompanyList(bean, userId);

//		TestsetBankTeller(bean, userId, "CGD2");
		TestsetBankTeller(bean, userId_2, "BES2");
		
		TestsetBankTeller(bean, userId, tellerId_2);
		TestgetBankTeller(bean, tellerId_1);
		TestgetBankTellerList(bean, "%");
		TestgetBankTellerNameList(bean, "%");
		ctx.close();
	}

	private static void TestsetBankTeller(IUserBean bean, Long userId, String tellerName)
			throws ConfigurationException {

		BankTeller bankTeller = new BankTeller(); 
		bankTeller.setName(tellerName);
		
//		bankTeller.setId( (long) 1);
				System.out.println("Testing  setBankTeller(user, BankTeller);");
		System.out.println("set BankTeller User/BankTeller " + userId + " "
				+ bankTeller.getId());
		bean.setBankTeller(userId, bankTeller);
	}

	private static void TestsetBankTeller(IUserBean bean, Long userId,
			Long tellerId) throws ConfigurationException {

		System.out.println("Testing  setBankTeller(user, BankTeller,tellerId);");
		System.out.println("set BankTeller User/BankTeller " + userId + " "
				+ tellerId);
		bean.setBankTeller(userId, tellerId);
	}

	private static void TestgetBankTeller(IUserBean bean, Long bankTellerId)
			throws ConfigurationException {

		System.out.println("Testing  getBankTeller(companyName)");
		System.out.println("Searching Bank Teller Name: " + bankTellerId);
		BankTeller bankTeller = bean.getBankTeller(bankTellerId);
		System.out.println("\t Result " + bankTeller.getName());
	}

	private static void TestgetBankTellerList(IUserBean bean,
			String filterPattern) throws ConfigurationException {

		System.out.println("Testing  getBankTellerList(filterPattern)");
		System.out.println("Searching BankTeller Name: " + filterPattern);
		List<BankTeller> bankTellers = bean.getBankTellerList(filterPattern);
		for (BankTeller bankTeller : bankTellers)
			System.out.println("\t Result " + bankTeller.getName());
	}

	private static void TestgetBankTellerNameList(IUserBean bean,
			String filterPattern) throws ConfigurationException {

		System.out.println("Testing  getBankTellerNameList(filterPattern)");
		System.out.println("Searching BankTeller Name: " + filterPattern);
		List<String> company = bean.getBankTellerNameList(filterPattern);
		for (String comp : company)
			System.out.println("\t Result " + comp);
	}

	private static void TestgetfollowedCompanyList(IUserBean bean, Long userId)
			throws ConfigurationException {

		System.out.println("Testing  getfollowedCompanyList(filterPattern)");
		List<Company> companies = bean.getfollowedCompanyList(userId);

		// System.out.println("\t Result " + companies);
		for (Company company : companies)
			System.out.println("\t Result " + company.getName());
	}

	private static void TestfollowCompany(IUserBean bean, Long userId,
			Long companyId) throws ConfigurationException {

		System.out.println("Testing  followCompany(user, company);");
		System.out.println("Adding following Company User/Company " + userId
				+ " " + companyId);
		bean.followCompany(userId, companyId);
	}

	private static void TestunfollowCompany(IUserBean bean, Long userId,
			Long companyId) throws ConfigurationException {

		System.out.println("Testing  unfollowCompany(user, company)");
		System.out.println("Adding following Company User/Company " + userId
				+ " " + companyId);
		bean.unfollowCompany(userId, companyId);
	}

	private static void TestgetCompany(IUserBean bean, Long companyId)
			throws ConfigurationException {

		System.out.println("Testing  getCompany(companyName)");
		System.out.println("Searching Company Name: " + companyId);
		Company company = bean.getCompany(companyId);
		System.out.println("\t Result " + company.getName());
	}

	private static void TestgetCompanyList(IUserBean bean, String filterPattern)
			throws ConfigurationException {

		System.out.println("Testing  getCompanyList(filterPattern)");
		System.out.println("Searching Company Name: " + filterPattern);
		List<Company> company = bean.getCompanyList(filterPattern);
		for (Company comp : company)
			System.out.println("\t Result " + comp.getName());
	}

	private static void TestgetCompanyNameList(IUserBean bean,
			String filterPattern) throws ConfigurationException {

		System.out.println("Testing  getCompanyNameList(filterPattern)");
		System.out.println("Searching Company Name: " + filterPattern);
		List<String> company = bean.getCompanyNameList(filterPattern);
		for (String comp : company)
			System.out.println("\t Result " + comp);
	}

	private static void TestgetUser(IUserBean bean, String filterPattern)
			throws ConfigurationException {

		System.out.println("Testing  getName(user)");
		System.out.println("Searching Company Name: " + filterPattern);
		User company = bean.getUser(filterPattern);
		System.out.println("\t Result " + company.getName());
	}

}
