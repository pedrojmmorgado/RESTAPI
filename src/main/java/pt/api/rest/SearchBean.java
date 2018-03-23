package pt.api.rest;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

/**
 * Search Bean for REST API.
 * @author PMorgado
 *
 */
@Stateless
public class SearchBean implements ISearchService {
	private static final Logger LOGGER = Logger.getLogger(SearchBean.class);

	@EJB
	private StartupBean configs;

	/* (non-Javadoc)
	 * @see pt.api.rest.ISearchService#searchByQuery(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> searchByQuery(String query, int per_page, int page, String orderBy) {
		return this.configs.getProvider().searchByQuery(query, per_page, page, orderBy);
	}

	/* (non-Javadoc)
	 * @see pt.api.rest.ISearchService#searchAllByQuery(java.lang.String, int, int, java.lang.String)
	 */
	@Override
	public Response searchAllByQuery(String query, int per_page, int page, String orderBy) {
		return this.configs.getProvider().searchAllByQuery(query, per_page, page, orderBy);
	}


}