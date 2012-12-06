package eai.msejdf.esb;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Long id;
	protected String username;
    protected String name;
    protected String mailAddress;
    private Integer emailCount;
    
	/**
	 * Gets the id of the user
	 * @return id of the user
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id of the user
	 */
	public void setId(Long id) {
		this.id = id;
	}
    
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

	/**
	 * @return
	 */
	public Integer getEmailCount() {
		return emailCount;
	}

	/**
	 * @param emailCount
	 */
	public void setEmailCount(Integer emailCount) {
		this.emailCount = emailCount;
	}

}
