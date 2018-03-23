package pt.api.rest.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import pt.api.rest.ClientException;
import pt.api.rest.ClientUtils;
import pt.api.rest.StartupBean;

/**
 * GitHub Bean Provider.
 * @author PMorgado
 *
 */
@Stateless
@LocalBean
public class GitHubProvider implements IProvider {
	
	private static final String URL_SEPARATOR = "/";

	private static final String SEARCH_OPERATION = "search";

	private static final String QUERY_STRING_SEPARATOR = "&";

	private static final Logger LOGGER=Logger.getLogger(GitHubProvider.class);
	
	@EJB
	private StartupBean configs;
	

	/* (non-Javadoc)
	 * @see pt.rest.api.provider.IProvider#getProviderType()
	 */
	@Override
	public ProviderType getProviderType() {
		return ProviderType.GITHUB;
	}
	
	/* (non-Javadoc)
	 * @see pt.rest.api.provider.IProvider#searchByQuery(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> searchByQuery(String query, int per_page, int page, String orderBy) {
		
		if (query != null && !query.isEmpty()) {
			
			Client client = ClientBuilder.newClient();

			// Process parameters and ensure default values
			query = this.processParameters(query, per_page, page, orderBy);

			// request
			WebTarget target = client.target(configs.getApiBaseUrl() + URL_SEPARATOR + SEARCH_OPERATION + URL_SEPARATOR + query);

			// response
			Response resp = target.request().header("Authorization", configs.getBasicAuthentication()).get();

			// format result
			try {
				List<Map<String, Object>> articles = (List<Map<String, Object>>) ClientUtils.getResponseEntity("searchByQuery", resp, LOGGER);

				return this.formatData(articles);
			} catch (ClientException e) {
				LOGGER.error("", e);
			}
		}
		
		return Collections.emptyList();
	}
	
	/* (non-Javadoc)
	 * @see pt.rest.api.provider.IProvider#searchByQuery(java.lang.String)
	 */
	@Override
	public Response searchAllByQuery(String query, int per_page, int page, String orderBy) {
		
		Object result = null;
		
		if (query != null && !query.isEmpty()) {
			
			Client client = ClientBuilder.newClient();

			// Process parameters and ensure default values
			query = this.processParameters(query, per_page, page, orderBy);

			// request
			WebTarget target = client.target(configs.getApiBaseUrl() + URL_SEPARATOR + SEARCH_OPERATION + URL_SEPARATOR + query);

			// response
			Response resp = target.request().header("Authorization", configs.getBasicAuthentication()).get();
			
			result = resp.readEntity(Object.class);
			
		}else{
			result = Collections.emptyList();
		}
			
		
		return Response.status(Status.OK).entity(result).build();
	}

	/**
	 * Process parameters and ensure default values
	 * @param query
	 * @param per_page
	 * @param page
	 * @param orderBy
	 * @return
	 */
	private String processParameters(String query, int per_page, int page, String orderBy) {

		//ensure page number
		if(page>=0){
			query+=QUERY_STRING_SEPARATOR.concat("page=") + page; // The page number should be changeable by a query string parameter
		}

		//ensure default values per page
		query+=QUERY_STRING_SEPARATOR.concat("per_page=") + per_page; //The number of hits per page should be 25 by default, but must be changeable by a query string parameter

		//ensure sort method
		query+=QUERY_STRING_SEPARATOR.concat("sort=").concat(orderBy); // The sorting should be by score, but must be changeable by a query string parameter
		
		return query;
	}
	
	
	/**
	 * Format result data.
	 * @param entries
	 * @return
	 */
	private List<Map<String, Object>> formatData(final List<Map<String, Object>> entries) {
		final List<Map<String, Object>> results = new ArrayList<>();
		
		for(final Map<String, Object> entry : entries) {
			final Map<String, Object> entryResult = new HashMap<>();
			
			entryResult.put("ownerName", entry.get("owner_name"));
			entryResult.put("repositoryName", entry.get("repository_name"));
			entryResult.put("fileName", entry.get("file_name"));
			
			results.add(entryResult);
		}
		
		return results;
	}

}