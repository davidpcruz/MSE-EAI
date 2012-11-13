package eai.msejdf.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity implementation class for Entity: Company
 *
 */
public class Company implements Serializable {

	 
	private Long id;
    protected String name;
	private Address address;
    protected String website;
	private List<User> subscribedUsers = new ArrayList<User>();;

	// Stocks
	private StockInfo stockInfo;
    
	private static final long serialVersionUID = 1L;
	
	public Company() {
		super();
	} 
	
	public Long getId() {
 		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the subscribedUsers
	 */
	public List<User> getSubscribedUsers()
	{
		return subscribedUsers;
	}

	/**
	 * @param subscribedUsers the subscribedUsers to set
	 */
	public void setSubscribedUsers(List<User> subscribedUsers)
	{
		this.subscribedUsers = subscribedUsers;
	}

	public Address getAddress()
	{
		return this.address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		this.website = website;
	}
	
	@Override
	public String toString() {
	    String newLine = System.getProperty("line.separator");
	    
	    return  "id: " + this.id + newLine +
	    		"name: " + this.name + newLine + 
	    		((null != this.address) ? this.address.toString() : "") + 
	    		"website: " + this.website + newLine ;
 	}

	/**
	 * @return the stockInfo
	 */
	public StockInfo getStockInfo()
	{
		return stockInfo;
	}

	/**
	 * @param stockInfo the stockInfo to set
	 */
	public void setStockInfo(StockInfo stockInfo)
	{
		this.stockInfo = stockInfo;
	} 
   
}
