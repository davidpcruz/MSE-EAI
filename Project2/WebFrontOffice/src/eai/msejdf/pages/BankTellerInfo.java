package eai.msejdf.pages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.pagedata.BankTellerPageData;
import eai.msejdf.persistence.User;

@ManagedBean
@ViewScoped
public class BankTellerInfo extends BankTellerPageData{

	public BankTellerInfo() throws NamingException, ConfigurationException {
		super();
	}
	
	public User getUser() {
		return this.user;
	}
}
