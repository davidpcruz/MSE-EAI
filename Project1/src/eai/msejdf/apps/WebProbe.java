package eai.msejdf.apps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import eai.msejdf.web.ParseStocksPlugin;
import eai.msejdf.web.Parser;

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

		WebProbe probe = new WebProbe(args[PROGRAM_ARG_INDEX__URL]);
		
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
		
		if (0 == args.length)
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
		System.out.println("java WebProbe <web url>");
	}
	
	public WebProbe(String url)
	{
		this.webUrl = url;
	}
	
	public void run() throws Exception
	{
		Parser webParser = new ParseStocksPlugin(this.webUrl); 

		webParser.parse(); //TODO: this has a return type. Handle it
		
		String message = this.createMessage(); //TODO: Implement this function with marshaling 
		
		try
		{
			// As we may have messages that were previously not delivered, we''l try to send them first to 
			// keep the same order
			this.writePendingMessages();

			// Now, dispatch our message
			this.writeMessage(message); //TODO:Replace this by JMS API
		}
		catch(Exception exception)
		{
			// As the dispatching of the message (or pending messages) failed, we'll save a local backup 
			// and retry on the next run
			this.saveMessageAsPending(message);
		}		
	}
	
	private void writeMessage(String message)
	{
		// TODO: This is a dummy function
		
		System.out.println("Message: " + message);
	}
	
	private void writePendingMessages() throws IOException
	{
		// Pending messages to write are each in a file in a dedicated directory. Retrieve pending file list
		File[] fileList = this.getFileList(this.DIRECTORY__PENDING_MESSAGES);
	
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
	
	private void saveMessageAsPending(String message) throws IOException
	{
		File outputFile = null; 
		
		// Create a unique file  based on the current time/date
		do
		{
			outputFile = new File(this.DIRECTORY__PENDING_MESSAGES + "/" + (new Date()).toString() + ".xml");
		}
		while (false == outputFile.createNewFile());

		// Save the message to the file
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile));
		fileWriter.write(message);
		fileWriter.close();
	}
	
	private File[] getFileList(String directoryPath)
	{
		File directory = new File(directoryPath);
		File[] fileList = directory.listFiles();

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
	
	private String createMessage() //TODO: This will have a parameter with the object with the data
	{
		return "test message"; //TODO: Replace this!!!
	}
}
