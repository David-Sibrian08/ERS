package DAO;

import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ServiceLocator {
	private static DataSource ers;
	private static Properties props;
	
	//look for the properties file and load it 
	
	static {
		InputStream stream = ServiceLocator.class.getClassLoader().getResourceAsStream("ers.properties");
		props  = new Properties();
		
		try {
			props.load(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static DataSource getERSdb() {
		if (ers == null) {
			ers = lookupERS();
		}
		return ers;
	}

	private static DataSource lookupERS() {
		try {
			Context ctxt = new InitialContext(props);
			DataSource ds = (DataSource) ctxt.lookup(props.getProperty("ersdb"));
			
			return ds;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
