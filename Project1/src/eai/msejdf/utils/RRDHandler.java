package eai.msejdf.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.rrd4j.DsType;
import org.rrd4j.ConsolFun;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.Sample;
import org.rrd4j.graph.RrdGraph;
import org.rrd4j.graph.RrdGraphDef;

public class RRDHandler {
	
	// Datasource corresponde ao nome de origem dos dados 
	// (uma varivel, que pode ser: euros, dollars, centimos, etc)
	private static String DATASOURCE_NAME = "euros";

	public static void createRRD (String fileName) throws IOException{
		
		// Ao mudar o step (que neste momento é 60) deve mudar tambem o numero de 
		// datapoints no primeiro arquivo (que é o 61) e deve ser igual a 3600 / step + 1
		RrdDef rrdDef = new RrdDef(fileName, 60);
		rrdDef.setStartTime((System.currentTimeMillis() / 1000l - 18144000l));
		rrdDef.addDatasource(DATASOURCE_NAME, DsType.GAUGE, 3600, 0, Double.NaN);
		rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 1, 61);
		rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 24, 244);
		rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 168, 244);
		rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 672, 244);
		rrdDef.addArchive(ConsolFun.AVERAGE, 0.99, 5760, 374);

		RrdDb rrdDb = new RrdDb(rrdDef);
		rrdDb.close();
	}
	
	/**
	 * 
	 * @param fileName - nome do ficheiro RRD
	 * @param timestamp - timestamp of reading (Epoch timestamp em SEGUNDOS)
	 * @param value - value reading
	 * @throws IOException
	 */
	public static void updateRRD (String fileName, long timestamp, float value) throws IOException {
		RrdDb rrdDb = new RrdDb(fileName);
		Sample sample = rrdDb.createSample();
		sample.setAndUpdate(timestamp+":"+value);
		rrdDb.close();
	}
	
	/**
	 * 
	 * @param fileName - nome do ficheiro RRD
	 * @param imageName - nome do ficheiro da imagem
	 * @param timeOffset - define a janela de tempo em SEGUNDOS
	 * @throws IOException 
	 */
	public static void createRRDGraph (String fileName, String imageName, long timeOffset) throws IOException {

		long ts = System.currentTimeMillis() / 1000;

		RrdGraphDef graphDef = new RrdGraphDef();
		graphDef.setTimeSpan(ts-timeOffset, ts);
		graphDef.datasource(DATASOURCE_NAME, fileName, DATASOURCE_NAME, ConsolFun.AVERAGE);
		graphDef.line(DATASOURCE_NAME, new Color(0xFF, 0, 0), null, 1);
		graphDef.setUnitsExponent(0);
		graphDef.setVerticalLabel(DATASOURCE_NAME);
		graphDef.setFilename(imageName);
		RrdGraph graph = new RrdGraph(graphDef);
		BufferedImage bi = new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
		graph.render(bi.getGraphics()); 
	}
}
