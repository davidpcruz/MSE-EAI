package eai.msejdf.user;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.session.SessionManager;
import eai.msejdf.exception.ConfigurationException;
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
		//tmp for debug this.user = bean.getUser(SessionManager.getProperty(SessionManager.USERNAME_PROPERTY));
		this.user = new User();
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
		//this.user.setBirthDate(new Date(birthDate));
	}
	
	
}
