package eai.msejdf.web.session;

import javax.faces.context.FacesContext;

/**
 * Support class for session management
 */
public class SessionManager {
	public static final String USERNAME_PROPERTY = "username";
	
	public static void setProperty(String name, String value)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put(name, value);
	}

	public static String getProperty(String name)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		return (String)context.getExternalContext().getSessionMap().get(name);
	}	

	public static String getRequestParameter(String name)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		return (String)context.getExternalContext().getRequestParameterMap().get(name);
	}	
}
