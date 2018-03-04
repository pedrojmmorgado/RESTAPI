package pt.api.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * TODO: add more test scenarios
 * @author Altran
 *
 */
@RunWith(Arquillian.class)
@RunAsClient
public class ServiceTest { 
	private static final Logger LOGGER = Logger.getLogger(ServiceTest.class);


	private URL deploymentUrl;
	

	@BeforeClass
	public static void initResteasyClient() {
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
	}
	
	@Before
	public void initializeDeploymentURL() throws UnsupportedEncodingException, MalformedURLException {
		try {
			deploymentUrl = new URL("http://localhost:8080/restapi/search");
			LOGGER.infov("Service URL -> {0}", deploymentUrl);
		} catch (MalformedURLException e) {
			LOGGER.error("Failed creating URL object", e);
			throw e;
		}
	}

	@Test
	public void testRequest() throws Exception {
		final Client client=ClientBuilder.newClient();
		
		//TODO: add more test scenarios
		final WebTarget target=client.target(deploymentUrl.toExternalForm() + "/search");
		
		LOGGER.infov("Target: {0}", target.getUri().toString());
		
		Response resp=target
				.request()
				.get();

		assertFalse("Resource not found.", resp.getStatus() == 404);
		assertTrue("Authenticated request failed: " + resp.getStatus(), resp.getStatus() == 200);
	}
	
}