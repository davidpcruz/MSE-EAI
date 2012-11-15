package eai.msejdf.pages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import eai.msejdf.pagedata.SecurityPageData;

@ManagedBean(name="account")
@ViewScoped
public class Account extends SecurityPageData{

	private String currentPassword;
	private String confirmPassword;

	
	public Account() throws NamingException {
		super();
	}


	public String getCurrentPassword()
	{
		return currentPassword;
	}


	public void setCurrentPassword(String currentPassword)
	{
		this.currentPassword = currentPassword;
	}


	public String getConfirmPassword()
	{
		return confirmPassword;
	}


	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}
	
	/**
	 * Change passord of the user
	 *
	 * @return true, if successful
	 */
	public boolean changePassword()
	{
		return true;
	}	
}
