package eai.msejdf.web.session;

import javax.faces.context.FacesContext;

/**
 * Support class for session management
 */
public class SessionManager {
	public static final String USERNAME_PROPERTY = "username";
	
	/**
	 * Sets the property.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public static void setProperty(String name, String value)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put(name, value);
	}

	/**
	 * Removes the property.
	 *
	 * @param name the name
	 */
	public static void removeProperty(String name)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (context.getExternalContext().getSessionMap().containsKey(name))
		{
			context.getExternalContext().getSessionMap().remove(name);
		}
	}
	
	/**
	 * Gets the property.
	 *
	 * @param name the name
	 * @return the property
	 */
	public static String getProperty(String name)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		return (String)context.getExternalContext().getSessionMap().get(name);
	}	

	/**
	 * Gets the request parameter.
	 *
	 * @param name the name
	 * @return the request parameter
	 */
	public static String getRequestParameter(String name)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		return (String)context.getExternalContext().getRequestParameterMap().get(name);
	}	
}
