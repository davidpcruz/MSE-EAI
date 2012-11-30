package eai.msejdf.pages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.pagedata.UserPageData;

@ManagedBean
@ViewScoped
public class UserInfo extends UserPageData{

	public UserInfo() throws NamingException, ConfigurationException {
		super();
	}
}
