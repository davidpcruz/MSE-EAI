/**
 * 
 */
package eai.msejdf.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.xml.transform.TransformerException;


import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.OutputKeys;

/**
 * @author joaofcr
 *
 */
public class Transform
{


	public static void xmlTransformation (String xmlFile, String xsltFile) throws TransformerException, UnsupportedEncodingException{
       
    	OutputStream resultFile = null;
 
    	InputStream xmlStream = new ByteArrayInputStream(xmlFile.getBytes("UTF8"));
    	StreamSource source = new StreamSource(xmlStream);
    	
    	 // Source source = (Source) new ByteArrayInputStream(xmlFile.getBytes("UTF8")); 

    	  Source xsl = new StreamSource(xsltFile);
		  Result result = new StreamResult(resultFile);

		  TransformerFactory factory = TransformerFactory.newInstance();
		  Transformer transformer = factory.newTransformer(xsl);
		  transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		  transformer.transform(source, result);
		  
		  String s = resultFile.toString(); 
		  System.out.println(resultFile.toString());
    }
	/**
	* @param args
	* @throws JMSException 
	* @throws NamingException 
	 * @throws TransformerException 
	 * @throws UnsupportedEncodingException 
	*/
	public static void main(String[] args) throws NamingException, TransformerException, UnsupportedEncodingException {

		String xsltFile = "C:\\Users\\joaofcr\\workspace\\Sender\\src\\StockMarket.xsl";
		
		
		String xmlMsg =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<?xml-stylesheet type=\"text/xsl\" href=\"StockMarket.xsl\"?>\n" +
				"<stocks timestamp=\"1308046204003\">\n" + 
				"<stock>\n" + 
				"<company>\n" +
				"<name>MSE</name>\n" +
				"<address>DEI</address>\n" +
				"<website>http://dei.uc.pt</website>\n" +
				"</company>\n" +
				"<cotation>\n" +
				"<rate>3.3</rate>\n" +
				"<change>1.8</change>\n" +
				"<maximum>3.45</maximum>\n" +
				"<minimum>3</minimum>\n" +
				"<quantity>43442</quantity>\n" +
				"<prevclose>3.1</prevclose>\n" +
				"</cotation>\n" +
				"</stock>\n" +
				"</stocks>" ;

				
				//System.out.println("XML:\n \n" + xmlMsg);
		//System.out.println("XSLT:\n \n" + xsltFile);
		//System.out.println(xmlMsg);
	    xmlTransformation(xmlMsg,xsltFile);
        


	}

}
