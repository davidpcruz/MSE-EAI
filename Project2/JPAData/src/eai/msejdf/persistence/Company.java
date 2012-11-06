package eai.msejdf.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Entity implementation class for Entity: Company
 *
 */
public class Company implements Serializable {

	 
	private Integer id;
    protected String name;
	private Address address;
    protected String website;
	private List<User> subscribedUsers;

	// quotation fields
	private BigDecimal lastQuotation;
	private String time;
	private BigDecimal variation;
    private BigInteger quantity;
    private BigDecimal maximum;
    private BigDecimal minimum;
    private BigDecimal purchase;
    private BigDecimal sell;	
    
	private static final long serialVersionUID = 1L;	
	public Company() {
		super();
	} 
	
	public Integer getId() {
 		return this.id;
	}

	public void setId(Integer id) {
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

	public BigDecimal getLastQuotation()
	{
		return lastQuotation;
	}

	public void setLastQuotation(BigDecimal lastQuotation)
	{
		this.lastQuotation = lastQuotation;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public BigDecimal getVariation()
	{
		return variation;
	}

	public void setVariation(BigDecimal variation)
	{
		this.variation = variation;
	}

	public BigInteger getQuantity()
	{
		return quantity;
	}

	public void setQuantity(BigInteger quantity)
	{
		this.quantity = quantity;
	}

	public BigDecimal getMaximum()
	{
		return maximum;
	}

	public void setMaximum(BigDecimal maximum)
	{
		this.maximum = maximum;
	}

	public BigDecimal getMinimum()
	{
		return minimum;
	}

	public void setMinimum(BigDecimal minimum)
	{
		this.minimum = minimum;
	}

	public BigDecimal getPurchase()
	{
		return purchase;
	}

	public void setPurchase(BigDecimal purchase)
	{
		this.purchase = purchase;
	}

	public BigDecimal getSell()
	{
		return sell;
	}

	public void setSell(BigDecimal sell)
	{
		this.sell = sell;
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
	
   
}
