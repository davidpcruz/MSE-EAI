package eai.msejdf.apps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import eai.msejdf.web.ParseStocksPlugin;
import eai.msejdf.web.Parser;

/**
 * Application class that connects to a web site and parses the page content to retrieve specific data, which is 
 * transformed and dispatched through the JMS for further processing by other clients
 * 
 * @author NB12588
 *
 */
public class WebProbe
{
	private final static int PROGRAM_ARG_INDEX__URL = 0;
	private final static String DIRECTORY__PENDING_MESSAGES = "./pending/";
	
	private String webUrl = null;
	
	/**
	 * Main application entry point
	 * @param args Parameters to supply to the application
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		validateArgs(args);

		WebProbe probe = new WebProbe(args[WebProbe.PROGRAM_ARG_INDEX__URL]);
		
		probe.run();
	}

	/**
	 * Validates a parameter list against what is expected/valid
	 *   
	 * @param args Parameter list to be validated
	 */
	public static void validateArgs(String[] args)
	{
		// Expected call syntax: "java WebProbe <web url>"
		
		if (1 > args.length)
		{
			printHelp();			
			System.exit(-1);			
			//Does not reach this point
		}
		// Parameters are assumed ok (no url validation is performed)
	}
	
	/**
	 * Prints some help information on how to call the program
	 */
	public static void printHelp()
	{
		System.out.println("Call usage:");
		System.out.println("java WebProbe <web url> <parser plugin>");
	}
	
	/**
	 * Constructs an instance of this class
	 * @param url Web site url to process
	 */
	public WebProbe(String url)
	{
		this.webUrl = url;
	}
	
	/**
	 * Main processing function. It parses a web page to extract specific content, sending a transformed 
	 * representation to remote clients for further processing
	 *  
	 * @throws Exception
	 */
	public void run() throws Exception
	{	
		String message = null;
		
		// Create an instance of the plugin
		//
		// Note: This is currently harcoded. The idea is to dynamically load this plugin based on a provided reference
		//		 which will allow the reuse of this application with different parser plugins
		Parser webParser = new ParseStocksPlugin(this.webUrl);		
		Object parsedDataObject = webParser.parse(); 
		
		try
		{
			message = this.createMessage(parsedDataObject);  
			
			// As we may have messages that were previously not delivered, we''l try to send them first to 
			// keep the same order
			this.writePendingMessages();

			// Now, dispatch our message
			this.writeMessage(message); 
		}
		catch(Exception exception)
		{
			// As the dispatching of the message (or pending messages) failed, we'll save a local backup 
			// and retry on the next run (if we hava a message to be processed)
			if (null != message)
			{
				this.saveMessageAsPending(message);
			}
		}		
	}

	/**
	 * Creates a message string from a data object
	 *  
	 * @param data object containing the data to be used to create a message
	 * @return String message representing the data object
	 * @throws JAXBException 
	 */
	private String createMessage(Object data) throws JAXBException
	{
		// Convert the object data into an XML
		StringWriter stringWriter = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance( WebProbe.class.getPackage().getName());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);		
        marshaller.marshal(data, stringWriter);

        return stringWriter.toString();		
	}
	
	/**
	 * Dispatches the message through the JMS for clients to further process it
	 *   
	 * @param message Message to be dispatched
	 */
	private void writeMessage(String message)
	{
		// TODO: call JMS write engine
		
		System.out.println("Message: " + message); //TODO: Remove
	}
	
	/**
	 * Writes messages through the JMS that failed to be dispatched in previous calls
	 *   
	 * @throws IOException
	 */
	private void writePendingMessages() throws IOException
	{
		// Pending messages to write are each in a file in a dedicated directory. Retrieve pending file list
		File[] fileList = this.getPendingMessagesFileList();
	
		if (null == fileList)
		{
			// No pending messages to process
			return;
		}
		
		for (File messageFile : fileList)
		{
			// Retrieve the message saved in the file
			BufferedReader fileReader= new BufferedReader(new FileReader(messageFile));
			String message = fileReader.readLine();
			fileReader.close();

			// Dispatch the message
			this.writeMessage(message);

			// At this point, the dispatch operation was successful, so eliminate file, as we don't need to send this message again
			messageFile.delete();
		}
	}
	
	/**
	 * Stores locally a copy of the message to go through a send-retry process on the next call to this application
	 * 
	 * @param message Message to be saved
	 * @throws IOException
	 */
	private void saveMessageAsPending(String message) throws IOException
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
		File outputFile = null; 
		
		// Create a unique file based on the current time/date
		do
		{
			outputFile = new File(WebProbe.DIRECTORY__PENDING_MESSAGES + "/" + dateFormatter.format(new Date()) + ".xml"); 
		}
		while (false == outputFile.createNewFile());

		// Save the message to the file
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile));
		fileWriter.write(message);
		fileWriter.close();
	}
	
	/**
	 * Retrieves a list of files in which each contains a messages that is pending to be dispatched
	 * 
	 * @return Pending message file list 
	 */
	private File[] getPendingMessagesFileList()
	{
		File directory = new File(WebProbe.DIRECTORY__PENDING_MESSAGES);
		File[] fileList = directory.listFiles();
		
		if (null == fileList)
		{
			return null;
		}

		// Ensure returned file list is ordered chronologically, so it is processed in the correct order
		Arrays.sort(fileList, new Comparator<File>() 
			{
				public int compare(File file1, File file2)
				{
					// This comparer does not return 0 if modification time is the same, as it is irrelevant for this application
					return (file1.lastModified() < file2.lastModified()) ? -1 : 1;		
				}
			});
		
		return fileList;
	}	
}
