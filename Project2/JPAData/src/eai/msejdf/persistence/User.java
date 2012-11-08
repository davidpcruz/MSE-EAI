/**
 * 
 */
package eai.msejdf.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author dcruz
 *
 */
public class User implements Serializable
{
	private Long id; 
	private String username; 
	private String password;
	private String name;
	private Address address;
	private String phone;
	private String email;
	private Date birthDate; 
	private BankTeller bankTeller;
	private List<Company> subscribedCompanies;

	private static final long serialVersionUID = 1L;	
	
	public User() {
		super();
	} 
	   
	public Long getId() {
 		return this.id;
	}

	public void setId(Long id) {
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

	/**
	 * @return the bankTeller
	 */
	public BankTeller getBankTeller()
	{
		return bankTeller;
	}

	/**
	 * @param bankTeller the bankTeller to set
	 */
	public void setBankTeller(BankTeller bankTeller)
	{
		this.bankTeller = bankTeller;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the companies
	 */
	public List<Company> getSubscribedCompanies()
	{
		return subscribedCompanies;
	}

	/**
	 * @param companies the companies to set
	 */
	public void setSubscribedCompanies(List<Company> companies)
	{
		this.subscribedCompanies = companies;
	}
	
	@Override
	public String toString() {
	    String newLine = System.getProperty("line.separator");
	    
	    return  "id: " + this.id + newLine +
	    		"username: " + this.username + newLine + 
	    		"password: " + this.password + newLine + 
	    		"name: " + this.name + newLine + 
	    		((null != this.address) ? this.address.toString() : "") + 
	    		"phone: " + this.phone + newLine + 
	    		"email: " + this.email + newLine + 
	    		"birthDate: " + this.birthDate + newLine + 
	    		((null != this.bankTeller) ? this.bankTeller.toString() : ""); 
 	} 
	
}
