/**
 * 
 */
package eai.msejdf.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

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
	 *            xmlFile: String with the XML file content String 
	 *            xsltFile: String with the XSLT file address
	 * 
	 * @return String output: the HTML file content
	 */
	public static String xmlTransformation(final String xmlFile, final String xsltFile) throws TransformerException,
	        UnsupportedEncodingException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("xmlTransformation(String, String) - start"); //$NON-NLS-1$
		}
		
		// basic validations
		if (StringUtils.isNullOrEmpty(xmlFile))
		{
			throw new IllegalArgumentException("xmlFile");
		}
		
		if (StringUtils.isNullOrEmpty(xsltFile))
		{
			throw new IllegalArgumentException("xsltFile");
		}
		
		File xsltFS = new File(xsltFile);
						
		if (!xsltFS.exists())
		{
			throw new IllegalArgumentException("file doesn't exist");
		}
		
		InputStream xmlStream = new ByteArrayInputStream(xmlFile.getBytes(XMLConstants.FILE_ENCODING));
		StreamSource source = new StreamSource(xmlStream);
		Source xsl = new StreamSource(xsltFS);
		StringWriter writer = new StringWriter();

		// Transformer configuration
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, new StreamResult(writer));

		String output = writer.getBuffer().toString();

		if (logger.isDebugEnabled())
		{
			logger.debug("xmlTransformation(String, String) - end"); //$NON-NLS-1$
		}
		return output;
	}
}
