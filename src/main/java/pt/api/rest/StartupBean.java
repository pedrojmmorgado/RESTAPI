package pt.api.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.xml.bind.DatatypeConverter;

import org.jboss.logging.Logger;

import pt.api.rest.provider.IProvider;
import pt.api.rest.provider.ProviderFactory;

/**
 * Startup bean for REST API application.
 * @author PMorgado
 *
 */
@Singleton
@Startup
@LocalBean
public class StartupBean {

	private static final Logger LOGGER=Logger.getLogger(StartupBean.class);

	//Example configuration file properties in: "../../../../../../config/domain.properties"
	//Other alternative can be: using environment variables for configuration
	private static final String CONFIG_FILE_PROPERTY = "configuration.file"; 

	private static final String CONFIG_API_PROVIDER = "api.provider";
	private static final String CONFIG_API_BASE_URL = "api.baseUrl";
	private static final String CONFIG_API_USERNAME = "api.username";	
	private static final String CONFIG_API_PASSWORD = "api.password";

	private IProvider provider = null;
	
	private String apiBaseUrl=null;
	private String basicAuthentication;
	private String providerType;
	
	
	/**
	 * Initialize startup bean and load all configuration.
	 */
	@PostConstruct
	public void init() {
		
		ResourceBundle configuration = ResourceBundle.getBundle("configuration");
		LOGGER.infov("Loading configurations from {0}", configuration.getObject(CONFIG_FILE_PROPERTY));
		
		//Load properties from configuration file
		Properties props = new Properties();

		try (FileInputStream stream=new FileInputStream((String) configuration.getObject(CONFIG_FILE_PROPERTY))) {
			props.load(stream);
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid config file: "+configuration.getObject(CONFIG_FILE_PROPERTY),e);
		}
		
		//parse all properties
		processConfigurationProperties(props);

		//select provider via configuration
		this.provider = new ProviderFactory().getProvider(providerType);
	}

	/**
	 * Parse all properties
	 * @param props
	 */
	private void processConfigurationProperties(Properties props) {
		
		//parse all properties
		providerType = props.getProperty(CONFIG_API_PROVIDER);
		if (providerType == null || providerType.isEmpty()) {
			throw new IllegalArgumentException(CONFIG_API_PROVIDER + " is null or empty");
		}

		//URL	
		apiBaseUrl = props.getProperty(CONFIG_API_BASE_URL);
		if (apiBaseUrl == null || apiBaseUrl.isEmpty()) {
			throw new IllegalArgumentException(CONFIG_API_BASE_URL + " is null or empty");
		}
		
		//authentication
		initBasicAuthentication(props.getProperty(CONFIG_API_USERNAME), props.getProperty(CONFIG_API_PASSWORD));
	}
	
	/**
	 * get provider.
	 * @return
	 */
	public IProvider getProvider() {
		return provider;
	}


	/**
	 * get API base URL.
	 * @return
	 */
	public String getApiBaseUrl() {
		return apiBaseUrl;
	}

	/**
	 * Get basit authentication.
	 * @return
	 */
	public String getBasicAuthentication() {
		return basicAuthentication;
	}

	/**
	 * Initialize basic authentication
	 * @param username
	 * @param password
	 */
	private void initBasicAuthentication(String username, String password) {
		String token = username + ":" + password;
		try {
			basicAuthentication="BASIC " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			throw new IllegalStateException("Cannot encode with UTF-8", ex);
		}
	}

}
