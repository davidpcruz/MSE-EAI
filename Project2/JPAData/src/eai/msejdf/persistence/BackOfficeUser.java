package eai.msejdf.persistence;

import java.io.Serializable;

/**
 * Entity implementation class for Entity: BackOfficeUser
 *
 */
public class BackOfficeUser implements Serializable {

	 
	private Integer id; 
	private String username; 
	private String password;
	private static final long serialVersionUID = 1L;	
	
	public BackOfficeUser() {
		super();
	} 
	   
	public Integer getId() {
 		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	   
	public String getUsername() {
 		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	   
	public String getPassword() {
 		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
   
}
