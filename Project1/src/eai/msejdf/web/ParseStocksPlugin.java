package eai.msejdf.web;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author NB12588
 *
 * Plugin to parse stock data from a web site and return an object that represents that data
 * 
 */
public class ParseStocksPlugin extends Parser
{
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
	public void parse() throws IOException 
	{
		// Create a DOM representation of a web page
		Document webPageDoc = Jsoup.connect(this.webUrl).get();
		
		// Extract all rows of the tables that have cotation information
		Elements cotationInfoRows = webPageDoc.select("tr:has(td[class^=tituloforumbar]):gt(0) ~ tr");
		
		// Example: http://www.nextbolsa.com/cotacoes.php?action=euronext
		// 
		// Título 		Últ. Cotação 	Hora 	Variação 	Quantidade 	Máximo 	Mínimo 	Compra 	Venda
		// BPI Hist.    	0.83       16:35 	  1.34%    	  991,176    0.84    0.82    0.00    0.00    
		// ZON MULTI. Hist. 2.38       16:35 	  9.16%      2,621,838   2.40    2.19    0.00    0.00  
		
		for (Element cotationInfo : cotationInfoRows)
		{
			System.out.println("TEXT = " + cotationInfo.text());
			
			// Separates the different cotation information fields into a list of fields, excluding data
			// in the first field that is irrelevant ("Hist." for the example above)
			Elements cotationFields = cotationInfo.select("table td:eq(0), >td:gt(0)");
			
			for (Element el2 : cotationFields)
			{
				System.out.println("    VAL = " + el2.text());
			}			
		}
		
	}	
}
