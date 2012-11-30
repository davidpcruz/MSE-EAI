package eai.msejdf.pagedata;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.Address;
import eai.msejdf.persistence.User;
import eai.msejdf.user.IUserBean;
import eai.msejdf.utils.EJBLookupConstants;
import eai.msejdf.web.SessionManager;

public class UserPageData{
	
	private IUserBean userBean;
	private User user;

	public UserPageData() throws NamingException, ConfigurationException {
		InitialContext ctx = new InitialContext();
		
		this.userBean = (IUserBean) ctx.lookup(EJBLookupConstants.EJB_I_USER);
		
		String username = SessionManager.getProperty(SessionManager.USERNAME_PROPERTY);
		
		if (null != username)
		{
			this.user = this.userBean.getUser(username);
		}
		else
		{
			// If the username does not exist in the session, we may be in the creation process of a new user
			this.user = new User();
		}
		if (null == this.user.getAddress())
		{
			this.user.setAddress(new Address());
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean updateUser() {
		try {
			this.userBean.updateUser(this.user);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return true;
	}
}
