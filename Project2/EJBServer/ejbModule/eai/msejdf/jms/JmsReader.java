package eai.msejdf.jms;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
@LocalBean
public class JmsReader  {
	

	public JmsReader() {
		System.out.println("JmsReader: I'm Alive");
	
	}

	
}
