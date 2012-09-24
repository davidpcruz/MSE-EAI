/**
 * 
 */
package eai.msejdf.utils;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.xml.transform.TransformerException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Source;

import javax.xml.transform.OutputKeys;

/**
 * @author joaofcr
 * 
 */
public class Transform
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Transform.class);

	/**
	 * Transforms a XML in HTML using a XSLT
	 * 
	 * @throws JTransformerException
	 *             , UnsupportedEncodingException
	 * @param String
	 *            xmlFile: String with the XML file content String xsltFile:
	 *            String with the XSLT file address
	 * 
	 * @return String output: the HTML file content
	 */
	public static String xmlTransformation(String xmlFile, String xsltFile) throws TransformerException,
	        UnsupportedEncodingException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("xmlTransformation(String, String) - start"); //$NON-NLS-1$
		}

		InputStream xmlStream = new ByteArrayInputStream(xmlFile.getBytes("UTF8"));
		StreamSource source = new StreamSource(xmlStream);
		Source xsl = new StreamSource(xsltFile);
		StringWriter writer = new StringWriter();
		try
		{
			// Transformer configuration
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(xsl);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, new StreamResult(writer));
		} catch (TransformerException ex)
		{
			logger.error("run", ex); //$NON-NLS-1$
		}

		String output = writer.getBuffer().toString();

		if (logger.isDebugEnabled())
		{
			logger.debug("xmlTransformation(String, String) - end"); //$NON-NLS-1$
		}
		return output;
	}

	/**
	 * @param args
	 * @throws JMSException
	 * @throws NamingException
	 * @throws TransformerException
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws NamingException, TransformerException, UnsupportedEncodingException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		String xsltFile = "C:\\Users\\joaofcr\\workspace\\Sender\\src\\StockMarket.xsl";

		String xmlMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
		        + "<?xml-stylesheet type=\"text/xsl\" href=\"StockMarket.xsl\"?>\n"
		        + "<stocks timestamp=\"1308046204003\">\n" + "<stock>\n" + "<company>\n" + "<name>MSE</name>\n"
		        + "<address>DEI</address>\n" + "<website>http://dei.uc.pt</website>\n" + "</company>\n"
		        + "<cotation>\n" + "<rate>3.3</rate>\n" + "<change>1.8</change>\n" + "<maximum>3.45</maximum>\n"
		        + "<minimum>3</minimum>\n" + "<quantity>43442</quantity>\n" + "<prevclose>3.1</prevclose>\n"
		        + "</cotation>\n" + "</stock>\n" + "</stocks>";

		String resultFile = xmlTransformation(xmlMsg, xsltFile);

		System.out.println(resultFile);

		if (logger.isDebugEnabled())
		{
			logger.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

}
