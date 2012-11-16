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
import eai.msejdf.sort.CompanySort;
import eai.msejdf.sort.UserSort;
import eai.msejdf.user.IUserBean;
import eai.msejdf.admin.IAdmin;

public class TestAdmin {
	/**
	 * @param args
	 * @throws NamingException
	 * @throws IOException
	 * @throws SecurityException
	 * @throws ConfigurationException
	 */
	public static void main(String[] args) throws NamingException, IOException,
			SecurityException {

		InitialContext ctx = new InitialContext();
		IAdmin bean = (IAdmin) ctx.lookup(Resource.JNDI_ADMIN_BEAN);

		Long companyId = (long) 1;
		int sortType = 1;
		int ageThreshold = 1;

		try {
			TestgetUserList(bean, sortType, ageThreshold);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			TestgetUserFollowCompanyList(bean, companyId, sortType, ageThreshold);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			TestgetCompanyList(bean, "%C%", sortType);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ctx.close();
	}

	/**
	 * @param bean
	 * @param filterPattern
	 * @param sortType
	 * @throws ConfigurationException
	 */
	private static void TestgetCompanyList(IAdmin bean, String filterPattern, int sortType )
			throws ConfigurationException {

		System.out.println("Testing  getCompanyList(filterPattern)");
		System.out.println("Searching Company Name: " + filterPattern);
		List<Company> company = bean.getCompanyList(filterPattern, CompanySort.NAME_ASC);
		for (Company comp : company)
			System.out.println("\t Result " + comp.getName());
	}
	
	/**
	 * @param bean
	 * @param sortType
	 * @param ageThreshold
	 * @throws ConfigurationException
	 */
	private static void TestgetUserList(IAdmin bean, int sortType, int ageThreshold)
			throws ConfigurationException {

		System.out.println("Testing  getUserList");

		List<User> userList = bean.getUserList(ageThreshold, UserSort.BIRTHDAY_ASC);
		for (User user : userList)
			System.out.println("\t Result " + user.getName());
	}
	
	
	/**
	 * @param bean
	 * @param companyId
	 * @param sortType
	 * @param ageThreshold
	 * @throws ConfigurationException
	 */
	private static void TestgetUserFollowCompanyList(IAdmin bean,
			Long companyId, int sortType, int ageThreshold)
			throws ConfigurationException {

		System.out.println("Testing  UserFollowCompanyList");

		List<User> userList = bean.getUserFollowCompanyList(companyId,UserSort.BIRTHDAY_ASC);
		for (User user : userList)
			System.out.println("\t Result " + user.getName());
	}
}
