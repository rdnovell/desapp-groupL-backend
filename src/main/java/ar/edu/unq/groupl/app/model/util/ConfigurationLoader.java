package ar.edu.unq.groupl.app.model.util;

public class ConfigurationLoader {
	
	public final static String MAIL_ADDRESS_MAILJET = ConfigurationLoader.getMailAddress();
	
	public final static String MAIL_API_KEY_MAILJET = ConfigurationLoader.getMailApiKey();
	
	public final static String MAIL_API_SECRET_MAILJET = ConfigurationLoader.getMailApiSecret();
	
	private static String getMailAddress() {
		return System.getenv("MAIL_ADDRESS");
	}
	
	private static String getMailApiKey() {
		return System.getenv("MAIL_API_KEY");
	}
	
	private static String getMailApiSecret() {
		return System.getenv("MAIL_API_SECRET");
	}

}