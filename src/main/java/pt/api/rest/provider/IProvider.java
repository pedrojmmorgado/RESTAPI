package pt.api.rest.provider;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

/**
 * Common Interface for all providers.
 * @author PMorgado
 *
 */
public interface IProvider {

	/**
	 * get provider type
	 * @return
	 */
	public abstract ProviderType getProviderType();

	/**
	 * Search by query and return data (ownerName, repositoryName, fileName)
	 * @param query
	 * @param per_page
	 * @param page
	 * @param orderBy
	 * @return
	 */
	List<Map<String, Object>> searchByQuery(String query, int per_page, int page, String orderBy);

	/**
	 * Search all by query and return all data
	 * @param query
	 * @param per_page
	 * @param page
	 * @param orderBy
	 * @return
	 */
	Response searchAllByQuery(String query, int per_page, int page, String orderBy);
	
	
}