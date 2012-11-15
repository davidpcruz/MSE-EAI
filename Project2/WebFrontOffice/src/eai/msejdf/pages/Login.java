package eai.msejdf.pages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import eai.msejdf.pagedata.SecurityPageData;

@ManagedBean
@ViewScoped
public class Login extends SecurityPageData{

	public Login() throws NamingException {
		super();
	}
}
