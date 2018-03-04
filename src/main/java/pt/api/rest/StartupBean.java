package pt.api.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.xml.bind.DatatypeConverter;

import org.jboss.logging.Logger;

import pt.rest.api.provider.IProvider;
import pt.rest.api.provider.ProviderFactory;

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

	private static final String CONFIG_FILE="../../../../../../config/configuration.properties";

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

		//Load properties from configuration file
		Properties props = new Properties();

		try (FileInputStream stream = new FileInputStream(CONFIG_FILE)) {
			props.load(stream);
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid config file: " + CONFIG_FILE, e);
		}

		//parse all properties
		providerType = props.getProperty(CONFIG_API_PROVIDER);
		if (providerType == null || providerType.isEmpty()) {
			throw new IllegalArgumentException(CONFIG_API_PROVIDER + " is null or empty");
		}

		apiBaseUrl = props.getProperty(CONFIG_API_BASE_URL);
		if (apiBaseUrl == null || apiBaseUrl.isEmpty()) {
			throw new IllegalArgumentException(CONFIG_API_BASE_URL + " is null or empty");
		}

		initBasicAuthentication(props.getProperty(CONFIG_API_USERNAME), props.getProperty(CONFIG_API_PASSWORD));

		//select provider via configuration
		this.provider = new ProviderFactory().getProvider(providerType);
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
