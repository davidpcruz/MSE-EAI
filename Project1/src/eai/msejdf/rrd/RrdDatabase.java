/**
 * 
 */
package eai.msejdf.rrd;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.Sample;
import org.rrd4j.graph.RrdGraph;
import org.rrd4j.graph.RrdGraphDef;

import eai.msejdf.utils.StringUtils;

/**
 * The Class RrdDatabase.
 *
 * @author dcruz
 */
public class RrdDatabase
{
	
	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(RrdDatabase.class);

	/** Logger for this class. */
	private static final boolean PRINT_RRD_DUMP = false;

	/** The rrd database name. */
	private String dbName;
	
	/** The data source name. */
	private String dataSourceName;
		
	/**
	 * Instantiates a new rrd database.
	 *
	 * @param fileName the file name
	 * @param dataSource the data source
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public RrdDatabase(String fileName, String dataSource) throws IOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("RrdDatabase(String, String) - start"); //$NON-NLS-1$
		}

		// basic validations
		if (StringUtils.isNullOrEmpty(fileName))
		{
			throw new IllegalArgumentException("fileName");
		}

		if (StringUtils.isNullOrEmpty(dataSource))
		{
			throw new IllegalArgumentException("dataSource");
		}
		
		this.dbName = fileName;
		this.dataSourceName = dataSource;
		
		// create the database (if it does not exist yet)
		// Changing the step will make the the number of data points on the first archive to change also
		// need to change the number of data points in the first archive (being HEARTBEAT_VALUE (ex:3600) / step + 1) 
		// check the las statement

		if (false == new File(this.dbName).exists())
		{
			RrdDef rrdDef = new RrdDef(this.dbName, 60); // Step seconds the number of seconds to store for each 
			// to check
			rrdDef.setStartTime((System.currentTimeMillis() / 1000l - 86400l)); // start 1 day before for now 86400
			
			// heartbeat 
			// Defines the amount of seconds the rrd database will store a NaN when not receiving data.
			rrdDef.addDatasource(this.dataSourceName, DsType.GAUGE, 3600, 0, Double.NaN);
			// Creates archives of the data we need
			// Archives logic
			// steps 
			// 	defines how many of these primary data points are used to build a consolidated data point which then goes into the archive.
			// rows 
			//	defines how many generations of data values are kept in an RRA. Obviously, this has to be greater than zero.			
			rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 1, 60);			// Minutes 
			rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 60, 24);			// Day  
			rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 24*60, 7);		// Week	
			rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 24*60*30, 30);	// Month
	
			RrdDb rrdDb = new RrdDb(rrdDef);
			rrdDb.close();
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("RrdDatabase(String, String) - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Update data.
	 *
	 * @param timestamp the timestamp
	 * @param value the value
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateData (long timestamp, float value) throws IOException 
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateData(long, float) - start"); //$NON-NLS-1$
		}

		// No basic validations				
		RrdDb rrdDb = new RrdDb(this.dbName);
		Sample sample = rrdDb.createSample();
		sample.setAndUpdate(timestamp+":"+value);

		if (PRINT_RRD_DUMP) 
		{
			logger.info("DUMP " + rrdDb.dump()); //$NON-NLS-1$
		}

		rrdDb.close();

		if (logger.isDebugEnabled())
		{
			logger.debug("updateData(long, float) - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Creates the rrd graph.
	 *
	 * @param imageName the image name
	 * @param timeOffset the time offset
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void createRRDGraph (String imageName, long timeOffset) throws IOException {
		if (logger.isDebugEnabled())
		{
			logger.debug("createRRDGraph(String, long) - start"); //$NON-NLS-1$
		}

		long ts = System.currentTimeMillis() / 1000;

		RrdGraphDef graphDef = new RrdGraphDef();
		graphDef.setTimeSpan(ts-timeOffset, ts);
		graphDef.datasource(this.dataSourceName, this.dbName, this.dataSourceName, ConsolFun.AVERAGE);
		graphDef.line(this.dataSourceName, new Color(0xFF, 0, 0), null, 1);
		graphDef.setUnitsExponent(0);
		graphDef.setVerticalLabel(this.dataSourceName);
		graphDef.setFilename(imageName);
		RrdGraph graph = new RrdGraph(graphDef);
		BufferedImage bi = new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
		graph.render(bi.getGraphics()); 

		if (logger.isDebugEnabled())
		{
			logger.debug("createRRDGraph(String, long) - end"); //$NON-NLS-1$
		}
	}
	
}
