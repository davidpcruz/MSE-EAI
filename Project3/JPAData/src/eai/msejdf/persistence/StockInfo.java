package eai.msejdf.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Entity implementation class for Entity: Company
 *
 */
public class StockInfo implements Serializable {

	 
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
	
	public StockInfo() {
		super();
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
	
	@Override
	public String toString() {
	    String newLine = System.getProperty("line.separator");
	    
	    return  "lastQuotation: " + this.lastQuotation + newLine + 
	    		"time: " + this.time + newLine + 
	    		"variation: " + this.variation + newLine + 
	    		"quantity: " + this.quantity + newLine +
	    		"maximum: " + this.maximum + newLine +
	    		"minimum: " + this.minimum + newLine +
	    		"purchase: " + this.purchase + newLine +
	    		"sell: " + this.sell + newLine;
 	} 
   
}
