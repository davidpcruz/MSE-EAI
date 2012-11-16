package eai.msejdf.pages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.pagedata.CompanyPageData;

@ManagedBean
@ViewScoped
public class CompanyInfo extends CompanyPageData {

	public CompanyInfo() throws NamingException, ConfigurationException {
		super();
	}

}
