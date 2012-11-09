package admin;

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
import eai.msejdf.admin.IAdmin;

public class TestAdmin {
	public static void main(String[] args) throws NamingException, IOException,
			SecurityException, ConfigurationException {

		InitialContext ctx = new InitialContext();
		IAdmin bean = (IAdmin) ctx.lookup(Resource.JNDI_ADMIN_BEAN);

		Long companyId = (long) 1;
		int sortType = 1;
		int ageThreshold = 1;

		TestgetUserList(bean, sortType, ageThreshold);
		TestgetUserFollowCompanyList(bean, companyId, sortType, ageThreshold);
		TestgetCompanyList(bean, "%C%", sortType);
		ctx.close();
	}

	private static void TestgetCompanyList(IAdmin bean, String filterPattern, int sortType )
			throws ConfigurationException {

		System.out.println("Testing  getCompanyList(filterPattern)");
		System.out.println("Searching Company Name: " + filterPattern);
		List<Company> company = bean.getCompanyList(filterPattern, sortType);
		for (Company comp : company)
			System.out.println("\t Result " + comp.getName());
	}
	
	private static void TestgetUserList(IAdmin bean, int sortType, int ageThreshold)
			throws ConfigurationException {

		System.out.println("Testing  getUserList");

		List<User> userList = bean.getUserList(
				sortType, ageThreshold);
		for (User user : userList)
			System.out.println("\t Result " + user.getName());
	}
	
	
	private static void TestgetUserFollowCompanyList(IAdmin bean,
			Long companyId, int sortType, int ageThreshold)
			throws ConfigurationException {

		System.out.println("Testing  UserFollowCompanyList");

		List<User> userList = bean.getUserFollowCompanyList(companyId,
				sortType, ageThreshold);
		for (User user : userList)
			System.out.println("\t Result " + user.getName());
	}
}
