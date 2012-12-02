package eai.msejdf.esb;

public class User {

    protected String username;
    protected String name;
    protected String mailAddress;
    
	/**
	 * Gets the username of the user
	 * @return username of the user
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username of the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the name of the user
	 * @return name of the user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the user
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the e-mail address of the user
	 * @return e-mail address of the user
	 */
	public String getMailAddress() {
		return mailAddress;
	}
	
	/**
	 * Sets the e-mail address of the user
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

}
