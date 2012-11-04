/**
 * 
 */
package eai.msejdf.data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dcruz
 *
 */
public class User implements Serializable
{
	private Integer id; 
	private String username; 
	private String password;
	private Address address;
	private String phone;
	private String email;
	private Date birthDate; 


	private static final long serialVersionUID = 1L;	
	
	public User() {
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

	/**
	 * @return the address
	 */
	public Address getAddress()
	{
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address)
	{
		this.address = address;
	}

	/**
	 * @return the phone
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate()
	{
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}
	
}
