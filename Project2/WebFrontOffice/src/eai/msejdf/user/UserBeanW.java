package eai.msejdf.user;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.session.SessionManager;
import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;


@ManagedBean
@ViewScoped
public class UserBeanW
{
	private IUserBean bean;
	private User user;
	private String birthDate;
 	
	
	public UserBeanW() throws NamingException, ConfigurationException
	{
		InitialContext ctx = new InitialContext();
	
		// TODO: Remove hardcoded
		this.bean = (IUserBean) ctx.lookup("ejb:P2EARDeploy/EJBServer/UserBean!eai.msejdf.user.IUserBean");

		//TODO
		this.user = bean.getUser(SessionManager.getProperty(SessionManager.USERNAME_PROPERTY));
	}
	
	public IUserBean bean()
	{
		return this.bean;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean updateUser()
	{
		//TODO: The exception should cause the user interface to be informed 
		try
		{
			this.bean.updateUser(this.user);	
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
		//this.user.setBirthDate(new Date(birthDate));
	}
	
	public List<Company> getFollowedCompanyList() throws ConfigurationException
	{
		return this.bean.getfollowedCompanyList(user.getId());
	}
	
	public List<Company> getCompanyList() throws ConfigurationException
	{
		return this.bean.getCompanyList("%");
	}
	
}
