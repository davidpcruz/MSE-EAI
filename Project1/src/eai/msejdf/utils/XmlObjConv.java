package eai.msejdf.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import eai.msejdf.data.Stock;


/**
 * Support class to marshall/unmarshall between an object and a string containing the XML
 *  
 * @author NB12588
 *
 */
public class XmlObjConv
{
	private String className = null;
	
	/**
	 * Creates an instance of this class
	 * 
	 * @param className Name of the class used in the conversion
	 */
	public XmlObjConv(String className)
	{
		if (null == className)
		{
			throw new IllegalArgumentException();			
		}
		this.className = className;
	}
	
	/**
	 * Converts an object into an XML string
	 * 
	 * @param data Object to be converted
	 * @return String containing converted XML
	 * @throws JAXBException
	 */
	public String Convert(Object data) throws JAXBException
	{
		StringWriter stringWriter = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(Stock.class.getPackage().getName());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);		
        marshaller.marshal(data, stringWriter);
		
        return stringWriter.toString();
	}

	/**
	 * Converts a string containing a XML into an object
	 * 
	 * @param xml String containing the XML to be converted
	 * @return Object representing the XML
	 * @throws JAXBException
	 */
	public Stock Convert(String xml) throws JAXBException
	{
        JAXBContext jaxbContext = JAXBContext.newInstance(Stock.class.getPackage().getName());
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (Stock)unmarshaller.unmarshal(new StringReader(xml));		
	}
}
