package eai.msejdf.web;

import org.apache.log4j.Logger;

import eai.msejdf.config.Configuration;
import eai.msejdf.data.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * @author NB12588
 *
 * Plugin to parse stock data from a web site and return an object that represents that data
 * It currently supports the following site:
 * 		http://www.nextbolsa.com/cotacoes.php?action=euronext
 * 
 * Data syntax for this site:
 * 
 * Título 		Últ. Cotação 	Hora 	Variação 	Quantidade 	Máximo 	Mínimo 	Compra 	Venda
 * BPI Hist.    	0.83       16:35 	  1.34%    	  991,176    0.84    0.82    0.00    0.00    
 * ZON MULTI. Hist. 2.38       16:35 	  9.16%      2,621,838   2.40    2.19    0.00    0.00  
 * 
 * Note: The "Hist" string under the title is a link in the page that is not required and shall 
 * therefore be filtered out 
 * 
 * Important: If the above syntax or number of elements changes, STOCK_ROW__ELEMENT_COUNT and
 * STOCK_ROW_INDEX__* must be update accordingly, as well as the parsing
 */
public class ParseStocksPlugin implements Parser
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ParseStocksPlugin.class);

	private static final int STOCK_ROW_INDEX__COMPANY = 0;
	private static final int STOCK_ROW_INDEX__LAST_QUOTATION = 1;
	private static final int STOCK_ROW_INDEX__QUOTATION_TIME = 2;
	private static final int STOCK_ROW_INDEX__VARIATION = 3;
	private static final int STOCK_ROW_INDEX__QUANTITY = 4;
	private static final int STOCK_ROW_INDEX__MAXIMUM = 5;
	private static final int STOCK_ROW_INDEX__MINIMUM = 6;
	private static final int STOCK_ROW_INDEX__BUY = 7;
	private static final int STOCK_ROW_INDEX__SELL = 8;
	private static final int STOCK_ROW__ELEMENT_COUNT = 9;	// NOTE: Update if number of elements changes	
	private static final Locale STOCK_NUMBER_FORMAT_LOCALE = Locale.US;
	private static final int CONNECTION_TIMEOUT = Configuration.getConnectionTimeOut(); // In mili seconds

	private String webUrl = null;
	
	/**
	 * Constructs the stock parsing object
	 * 
	 * @param url The target URL from which HTML contend will be fetched and parsed to retrieve stock data
	 */
	public ParseStocksPlugin(String url)
	{
		if ((null == url) || url.isEmpty())
		{
			throw new NullPointerException();
		}
		this.webUrl = url;
	}
	
	/* (non-Javadoc)
	 * @see eai.msejdf.web.Parser#parse()
	 */
	public Object parse() throws IOException 
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("parse() - start"); //$NON-NLS-1$
		}

		Document webPageDoc = null;
		Elements quotationInfoRows = null;
		
		// Create a DOM representation of a web page
		webPageDoc = Jsoup.parse(new URL(this.webUrl), ParseStocksPlugin.CONNECTION_TIMEOUT); 

		// Extract all rows of the tables that have quotation information
		quotationInfoRows = webPageDoc.select("tr:has(td[class^=tituloforumbar]):gt(0) ~ tr");		
		if (0 == quotationInfoRows.size())
		{
			// The parsed data is not as we are expecting, which means that we can't make assumptions
			// about the correctness of the fields. The safest thing to do is to not return anything and 
			// alert the user somehow.
			logger.error("parse(): ERROR - Web Page syntax from " + this.webUrl + " is not supported"); //$NON-NLS-1$			
			return null;			
		}
		
		Stocks stocks = new Stocks();
		
		stocks.setTimestamp(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));

		// Fill out our stock information object for each individual stock information client
		for (Element quotationInfo : quotationInfoRows)
		{
			try
			{
				Stock stockInfo = new Stock();

				// Separates the different quotation information fields into a list of fields, excluding data
				// in the first field that is irrelevant ("Hist." as described in the example presented in 
				// this class doc header)
				Elements quotationFields = quotationInfo.select("table td:eq(0) a, >td:gt(0)");

				if (ParseStocksPlugin.STOCK_ROW__ELEMENT_COUNT != quotationFields.size())
				{
					logger.error("parse() ERROR - Web Page syntax from " + this.webUrl + " contains stock values with wrong syntax. Ignore them"); //$NON-NLS-1$

					// The parsed data is not as we are expecting, which means that we can't make assumptions
					// about the correctness of the fields. However, since the page may contain stock values that 
					// are incomplete for one company, but not for the others, we'll continue trying to parse 
					// other fields. 

					continue;
				}
			
				// Fill the sock object with the information retrieved from the page 
				parseFields(quotationFields, stockInfo);
				
				stocks.getStock().add(stockInfo);
			}
			catch(ParseException | DatatypeConfigurationException exception)
			{
				logger.error("parse() ERROR - Web Page syntax from " + this.webUrl + " contains stock values with wrong syntax. Ignore them"); //$NON-NLS-1$
				// The parsed data is not as we are expecting, which means that we can't make assumptions
				// about the correctness of the fields. However, since the page may contain stock values that 
				// are incomplete for one company, but not for the others, we'll continue trying to parse 
				// other fields. 
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("parse() - end"); //$NON-NLS-1$
		}
		return stocks;
	}
	
	private void parseFields(Elements quotationFields, Stock stockInfo) throws ParseException, DatatypeConfigurationException
	{		
		String field = null;
		NumberFormat formatter = NumberFormat.getInstance(ParseStocksPlugin.STOCK_NUMBER_FORMAT_LOCALE);
		
		stockInfo.setCompany(new Company());
		stockInfo.setQuotation(new Quotation());
		
		Company company = stockInfo.getCompany();
		Quotation quotation = stockInfo.getQuotation();
		
		field = quotationFields.get(ParseStocksPlugin.STOCK_ROW_INDEX__COMPANY).text();
		company.setName(field);

		field = quotationFields.get(ParseStocksPlugin.STOCK_ROW_INDEX__LAST_QUOTATION).text();
		quotation.setLastQuotation(BigDecimal.valueOf(formatter.parse(field).doubleValue()));

		field = quotationFields.get(ParseStocksPlugin.STOCK_ROW_INDEX__QUOTATION_TIME).text();
		// Check that field matches the HH:mm time pattern
		if (false == field.matches("([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]"))
		{
			throw new InputMismatchException();
		}		
		quotation.setTime(field);

		field = quotationFields.get(ParseStocksPlugin.STOCK_ROW_INDEX__VARIATION).text();
		quotation.setVariation(BigDecimal.valueOf(formatter.parse(field).doubleValue()));

		field = quotationFields.get(ParseStocksPlugin.STOCK_ROW_INDEX__QUANTITY).text();
		quotation.setQuantity(BigInteger.valueOf(formatter.parse(field).longValue()));
		
		field = quotationFields.get(ParseStocksPlugin.STOCK_ROW_INDEX__MAXIMUM).text();
		quotation.setMaximum(BigDecimal.valueOf(formatter.parse(field).doubleValue()));

		field = quotationFields.get(ParseStocksPlugin.STOCK_ROW_INDEX__MINIMUM).text();
		quotation.setMinimum(BigDecimal.valueOf(formatter.parse(field).doubleValue()));

		field = quotationFields.get(ParseStocksPlugin.STOCK_ROW_INDEX__BUY).text();
		quotation.setPurchase(BigDecimal.valueOf(formatter.parse(field).doubleValue()));

		field = quotationFields.get(ParseStocksPlugin.STOCK_ROW_INDEX__SELL).text();
		quotation.setSell(BigDecimal.valueOf(formatter.parse(field).doubleValue()));
	}
}
