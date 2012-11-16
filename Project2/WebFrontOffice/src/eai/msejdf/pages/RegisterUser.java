package eai.msejdf.pages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.pagedata.SecurityPageData;
import eai.msejdf.pagedata.UserPageData;

@ManagedBean
@ViewScoped
public class RegisterUser extends SecurityPageData{

	private UserPageData userInfo;
	
	public RegisterUser() throws NamingException, ConfigurationException {
		super();
		
		this.userInfo = new UserPageData();
		
		// Associate the user information with the security control (required for registration)
		this.setUserRegistrationInfo(this.userInfo.getUser());
	}

	public UserPageData getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserPageData userInfo) {
		this.userInfo = userInfo;
	}	
}
