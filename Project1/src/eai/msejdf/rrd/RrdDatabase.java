/**
 * 
 */
package eai.msejdf.rrd;

import java.awt.Color;
import java.awt.image.BufferedImage;
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
 * @author dcruz
 *
 */
public class RrdDatabase
{
	private static final int HEARTBEAT_VALUE = 3600;

	private String dbName;
	
	private String dataSourceName;
		
	/**
	 * @param fileName
	 * @param dataSource
	 * @throws IOException
	 */
	public RrdDatabase(String fileName, String dataSource) throws IOException
	{
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
		
		// create the database
		// Changing the step will make the the number of data points on the first archive to change also
		// need to change the number of data points in the first archive (being HEARTBEAT_VALUE (ex:3600) / step + 1) 
		// check the las statement

		RrdDef rrdDef = new RrdDef(this.dbName, 60);
		// to check
		rrdDef.setStartTime((System.currentTimeMillis() / 1000l - 18144000l));
		rrdDef.addDatasource(this.dataSourceName, DsType.GAUGE, HEARTBEAT_VALUE, 0, Double.NaN);
		// Creates archives of the data we need
		// 
		rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 1, 61);			// Hour 60
		rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 24, 244);		// Day  24
		rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 168, 244);		// Week	24*7=168
		rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 672, 244);		// Month 168*4=672
		rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 5760, 374);		// ??

		RrdDb rrdDb = new RrdDb(rrdDef);
		rrdDb.close();
		
	}
	
	/**
	 * @param timestamp
	 * @param value
	 * @throws IOException
	 */
	public void updateData (long timestamp, float value) throws IOException 
	{
		// No basic validations				
		RrdDb rrdDb = new RrdDb(this.dbName);
		Sample sample = rrdDb.createSample();
		sample.setAndUpdate(timestamp+":"+value);
		rrdDb.close();
	}
	
	/**
	 * @param imageName
	 * @param timeOffset
	 * @throws IOException
	 */
	public void createRRDGraph (String imageName, long timeOffset) throws IOException {

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
	}
	
}
