/**
 * 
 */
package eai.msejdf.persistence;

import java.io.Serializable;

/**
 * @author dcruz
 *
 */
public class Address implements Serializable
{

	private Integer id; 
	private String address; 
	private String zipCode;
	private String city;
	private static final long serialVersionUID = 1L;	
	
	public Address() {
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
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode()
	{
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	/**
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	@Override
	public String toString() {
	    String newLine = System.getProperty("line.separator");
	    
	    return  "id: " + this.id + newLine +
	    		"address: " + this.address + newLine + 
	    		"zipCode: " + this.zipCode + newLine + 
	    		"city: " + this.city + newLine;
 	} 
}
