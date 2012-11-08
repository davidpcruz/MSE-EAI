package app.user;

import helper.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.exception.SecurityException;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;
import eai.msejdf.user.IUserBean;

public class TestUserBean {

	public static void main(String[] args) throws NamingException, IOException,
			SecurityException, ConfigurationException {

		InitialContext ctx = new InitialContext();
		IUserBean bean = (IUserBean) ctx.lookup(Resource.JNDI_USER_BEAN);

//		TestgetCompany(bean, "BCP");
//		TestgetUser(bean, "test");
//
//		TestgetCompanyList(bean, "%C%");
//		TestgetCompanyNameList(bean, "%C%");
//		TestfollowCompany(bean, "test", "BCP");
//		TestfollowCompany(bean, "test", "BPI");
		TestgetfollowedCompanyList(bean, "test");

	}

	private static void TestgetfollowedCompanyList(IUserBean bean, String user) throws ConfigurationException {

		System.out.println("Testing  getfollowedCompanyList(filterPattern)");
		List<Company> companies = bean.getfollowedCompanyList(user);
		
//		System.out.println("\t Result " +  companies);
		for(Company company : companies)
			System.out.println("\t Result " + company.getName());
	}
	
	private static void TestfollowCompany(IUserBean bean, String user,
			String company) throws ConfigurationException {

		System.out.println("Testing  getCompanyList(filterPattern)");
		System.out.println("Adding following Company User/Company " + user
				+ " " + company);
		bean.followCompany(user, company);
	}

	private static void TestgetCompany(IUserBean bean, String filterPattern)
			throws ConfigurationException {

		System.out.println("Testing  getCompany(companyName)");
		System.out.println("Searching Company Name: " + filterPattern);
		Company company = bean.getCompany(filterPattern);
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
