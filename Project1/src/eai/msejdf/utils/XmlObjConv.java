package eai.msejdf.utils;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

/**
 * Support class to marshall/unmarshall between an object and a string
 * containing the XML
 * 
 * @author NB12588
 * 
 */
public final class XmlObjConv
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(XmlObjConv.class);

	/**
	 * Converts an object into an XML string
	 * 
	 * @param data
	 *            Object to be converted
	 * @return String containing converted XML
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static String convertToXML(Object data) throws JAXBException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("convertToXML(Object) - start"); //$NON-NLS-1$
		}

		StringWriter stringWriter = new StringWriter();

		// Schema validation (try to get the schema)
		Schema schema = getClassXSDSchema(data.getClass());
		
		JAXBContext jaxbContext = JAXBContext.newInstance(data.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		// add the schema validation (if found)
		if (schema != null)
		{
			marshaller.setSchema(schema);
		}
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(data, stringWriter);

		String returnString = stringWriter.toString();
		if (logger.isDebugEnabled())
		{
			logger.debug("convertToXML(Object) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Converts a string containing a XML into an object
	 * 
	 * @param xml
	 *            String containing the XML to be converted
	 * @return Object representing the XML
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	// Suppress the warning on the task (is there other way)
	public static <T extends Object> T convertToObject(String xml, Class<T> classType) throws JAXBException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("convertToObject(String, Class<T>) - start"); //$NON-NLS-1$
		}

		// Schema validation (try to get the schema)
		Schema schema = getClassXSDSchema(classType);

		JAXBContext jaxbContext = JAXBContext.newInstance(classType);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		// add the schema validation (if found)
		if (schema != null)
		{
			unmarshaller.setSchema(schema);
		}

		T returnT = (T) unmarshaller.unmarshal(new StringReader(xml));
		if (logger.isDebugEnabled())
		{
			logger.debug("convertToObject(String, Class<T>) - end"); //$NON-NLS-1$
		}
		return returnT;
	}

	/**
	 * Gets the class xsd schema.
	 *
	 * @param classType the class type
	 * @return the class xsd schema
	 */
	private static Schema getClassXSDSchema(Class<?> classType)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getClassXSDSchema(Class<?>) - start"); //$NON-NLS-1$
		}

		Schema schema = null;

		try
		{

			String xsdSchemaName = String.format("%s.xsd", classType.getSimpleName());

			InputStream inputStr = classType.getResourceAsStream(xsdSchemaName);

			if (inputStr != null)
			{
				Source schemaFile = new StreamSource(inputStr);
				SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				schema = schemaFactory.newSchema(schemaFile);
			} 
			else
			{
				logger.warn(String.format("getClassXSDSchema(Class<?>) unable to load the %s",xsdSchemaName)); 
			}

		} catch (SAXException e)
		{
			logger.error("getClassXSDSchema(Class<?>) - exception ", e); //$NON-NLS-1$
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("getClassXSDSchema(Class<?>) - end"); //$NON-NLS-1$
		}
		return schema;
	}
}
