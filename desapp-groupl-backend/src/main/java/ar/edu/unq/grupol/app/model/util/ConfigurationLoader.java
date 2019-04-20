package ar.edu.unq.grupol.app.model.util;

import java.util.Optional;
import java.util.Properties;

public class ConfigurationLoader {
	
	private final static Properties PROPERTIES = ConfigurationLoader.getProperties();
	
	private final static String CONFIGURATION_FILE = "configuration.properties";
	
	public final static String MAIL_ADDRESS = getMailAddress();
	
	public final static String MAIL_API_KEY = getMailApiKey();
	
	public final static String MAIL_API_SECRET = getMailApiSecret();
	
	private static Properties getProperties() {
		Properties properties = new Properties();
		try {
			properties.load(ConfigurationLoader.class.getClassLoader().getResourceAsStream(CONFIGURATION_FILE));
		} catch (Exception e) {
			throw new RuntimeException("Cannot get the properties from the configuration.");
		}
		return properties;
	}
	
	private static String getMailAddress() {
		return getProperty("MAIL_ADDRESS");
	}
	
	private static String getMailApiKey() {
		return getProperty("MAIL_API_KEY");
	}
	
	private static String getMailApiSecret() {
		return getProperty("MAIL_API_SECRET");
	}
	
	private static String getProperty(String property) {
		return Optional.ofNullable(PROPERTIES.getProperty(property)).orElseThrow(() -> new RuntimeException("Cannot find property '" + property + "' in file '" + CONFIGURATION_FILE + "'."));
	}

}
