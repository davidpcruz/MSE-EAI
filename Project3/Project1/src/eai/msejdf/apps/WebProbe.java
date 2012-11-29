package eai.msejdf.apps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import eai.msejdf.config.Configuration;
import eai.msejdf.jms.ESBReplicator;
import eai.msejdf.jms.JMSSender;
import eai.msejdf.utils.XmlObjConv;
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
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WebProbe.class);

	/**
	 * Position of site URL in program argument list 
	 */
	private final static int PROGRAM_ARG_INDEX__URL = 0;

	/**
	 * Position of parser class in program argument list 
	 */
	private final static int PROGRAM_ARG_INDEX__PARSER_PLUGIN = 1;
	
	/**
	 * URL of the site that will be processed 
	 */
	private String webUrl = null;
	
	/**
	 * Name of the class that will be used to parse the web site 
	 */
	private String parserPlugin = null;
	
	/**
	 * Main application entry point
	 * @param args Parameters to supply to the application
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		logger.info("WebProbe: Running..."); //$NON-NLS-1$
		
		validateArgs(args);

		WebProbe probe = new WebProbe(args[WebProbe.PROGRAM_ARG_INDEX__URL], args[WebProbe.PROGRAM_ARG_INDEX__PARSER_PLUGIN]);
		
		probe.run();

		logger.info("WebProbe: Running...COMPLETE"); //$NON-NLS-1$
	}

	/**
	 * Validates a parameter list against what is expected/valid
	 *   
	 * @param args Parameter list to be validated
	 */
	public static void validateArgs(String[] args)
	{
		// Expected call syntax: "java WebProbe <web url> <parser plugin>"		
		if (2 > args.length)
		{
			printHelp();			
			System.exit(-1);			
			//We never reach this point
		}
		// Parameters are assumed ok (no url validation is performed)
	}
	
	/**
	 * Prints some help information on how to call the program
	 */
	public static void printHelp()
	{
		System.out.println("Error, invalid call parameters.");
		System.out.println("Use: java WebProbe <web url> <parser plugin class>");
		System.out.println("");
	}
	
	/**
	 * Constructs an instance of this class
	 * @param url Web site url to process
	 * @param parserPlugin Name of class that will parse the supplied url
	 */
	public WebProbe(String url, String parserPlugin)
	{
		this.webUrl = url;
		this.parserPlugin = parserPlugin;
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
		
		try
		{
			// Create an instance of the parser class plugin (dynamic load)
			ClassLoader classLoader = WebProbe.class.getClassLoader();
			@SuppressWarnings("unchecked")
			Class<Parser> loadedClass = (Class<Parser>)classLoader.loadClass(this.parserPlugin); 		
			Constructor<Parser> parserClassConstructor = loadedClass.getConstructor(new Class[]{String.class});		
			Parser webParser = (Parser)parserClassConstructor.newInstance(this.webUrl);			
			
			// Get an object representing the page content (parse web page)
			Object parsedDataObject = webParser.parse(); 
			if (null == parsedDataObject)
			{
				// There isn't much we can do, except alerting the user somehow.
				logger.error("parse(): ERROR - Web Page syntax from " + this.webUrl + " is not supported"); //$NON-NLS-1$
				return;
			}
			
			// Convert the object into a string with an XML representation of it 
			message = XmlObjConv.convertToXML(parsedDataObject);  
			
			// As we may have messages that were previously not delivered, we'll try to send them first to 
			// keep the same order
			this.writePendingMessages();

			// Now, dispatch our message
			this.writeMessage(message); 
		}
		catch(Exception exception)
		{
			logger.error("run()", exception); //$NON-NLS-1$

			// As the dispatching of the message (or pending messages) failed, we'll save a local backup 
			// and retry on the next run (if we have a message to be processed)
			if (null != message)
			{
				this.saveMessageAsPending(message);
			}
		}		
	}
	
	/**
	 * Dispatches the message through the JMS for clients to further process it
	 *   
	 * @param message Message to be dispatched
	 * @throws JMSException 
	 * @throws NamingException 
	 */
	private void writeMessage(String message) throws JMSException, NamingException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("writeMessage(String) - start"); //$NON-NLS-1$
		}

		if (null == message)
		{
			throw new NullPointerException("Message is empty");
		}
		
		JMSSender dataSender = new JMSSender(Configuration.getJmsTopicName());
		
		dataSender.start();
		dataSender.sendMessage(message);
		dataSender.close();

		// ESB specifics
		ESBReplicator esbrep = new ESBReplicator();
		esbrep.start();
		esbrep.sendMessage(message);
		esbrep.close();
		
		
		if (logger.isDebugEnabled())
		{
			logger.debug("writeMessage(String) - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Writes messages through the JMS that failed to be dispatched in previous calls
	 *   
	 * @throws IOException
	 * @throws JMSException 
	 * @throws NamingException 
	 */
	private void writePendingMessages() throws IOException, JMSException, NamingException
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
			String message = "";
			String line;
			
			// Retrieve the message saved in the file
			BufferedReader fileReader= new BufferedReader(new FileReader(messageFile));
			
			for (line = fileReader.readLine(); null != line; line = fileReader.readLine()) 
			{
			    message = message.concat(line);
			}

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
		String messageDir = createPendingMessagesDirectory();
		
		// Create a unique file based on the current time/date
		do
		{
			outputFile = new File(messageDir + "/" + dateFormatter.format(new Date()) + ".xml");
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
		File directory = new File(Configuration.getDefaultOutputDir() + Configuration.getPendingMessagesDirectory());
		File[] fileList = directory.listFiles();
		
		if (null == fileList)
		{
			// No messages pending to be sent
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
	
	/**
	 * Creates and returns the directory to hold pending messages
	 * 
	 * @return Path to directory
	 */
	private String createPendingMessagesDirectory()
	{
		File baseOutputDir = Configuration.getDefaultOutputDir();
		String pendingMessagesDir = Configuration.getPendingMessagesDirectory();

		File directory = new File(baseOutputDir.getPath() + pendingMessagesDir);

		if (!directory.exists())
		{
			directory.mkdir();
		}

		return directory.getAbsolutePath();
	}
	
}
