package eai.msejdf.pages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import eai.msejdf.pagedata.SecurityPageData;

@ManagedBean(name="account")
@ViewScoped
public class Account extends SecurityPageData{

	public Account() throws NamingException {
		super();
	}	
	
}
