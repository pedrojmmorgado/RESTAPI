package pt.rest.api.provider;

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
	 * @return
	 */
	List<Map<String, Object>> searchByQuery(String query);
	
	
}