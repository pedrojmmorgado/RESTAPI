package pt.api.rest.provider;

import java.util.List;
import java.util.Map;

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
	 * Search by query 
	 * @param query
	 * @param per_page
	 * @param page
	 * @param orderBy
	 * @return
	 */
	List<Map<String, Object>> searchByQuery(String query, int per_page, int page, String orderBy);
	
	
}