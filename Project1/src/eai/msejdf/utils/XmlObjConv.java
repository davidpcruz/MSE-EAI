package eai.msejdf.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


/**
 * Support class to marshall/unmarshall between an object and a string containing the XML
 *  
 * @author NB12588
 *
 */
public final class XmlObjConv
{
	
	/**
	 * Converts an object into an XML string
	 * 
	 * @param data Object to be converted
	 * @return String containing converted XML
	 * @throws JAXBException
	 */
	public static String convertToXML(Object data) throws JAXBException
	{
		StringWriter stringWriter = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(data.getClass());
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
	public static Object convertToObject(String xml, Class<?> classType) throws JAXBException
	{
        JAXBContext jaxbContext = JAXBContext.newInstance(classType);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return unmarshaller.unmarshal(new StringReader(xml));		
	}
}
