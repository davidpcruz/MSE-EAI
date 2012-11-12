/**
 * 
 */
package eai.msejdf.persistence;

import java.io.Serializable;

/**
 * @author dcruz
 *
 */
public class BankTeller implements Serializable
{

	private Long id; 
	private String name; 
	private Address address;
	private static final long serialVersionUID = 1L;	

	public BankTeller() {
		super();
	}
	
	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
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

	@Override
	public String toString() {
	    String newLine = System.getProperty("line.separator");
	    
	    return  "id: " + this.id + newLine +
	    		"name: " + this.name + newLine + 
	    		"address: " + this.address + newLine;
 	} 
	
}
