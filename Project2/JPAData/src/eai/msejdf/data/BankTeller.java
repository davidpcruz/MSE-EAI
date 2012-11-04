/**
 * 
 */
package eai.msejdf.data;

import java.io.Serializable;

/**
 * @author dcruz
 *
 */
public class BankTeller implements Serializable
{

	private Integer id; 
	private String name; 
	private String password;
	private Address address;
	private static final long serialVersionUID = 1L;	

	public BankTeller() {
		super();
	}
	
	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
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
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
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

}
