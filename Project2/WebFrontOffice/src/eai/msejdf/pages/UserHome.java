package eai.msejdf.pages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.pagedata.CompanyPageData;

@ManagedBean
@ViewScoped
public class UserHome extends CompanyPageData {

	public UserHome() throws NamingException, ConfigurationException {
		super();
	}
}
