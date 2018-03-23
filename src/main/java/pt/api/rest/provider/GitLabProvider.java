package pt.api.rest.provider;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import pt.api.rest.StartupBean;

/**
 * GitLab Bean Provider.
 * @author PMorgado
 *
 */
@Stateless
@LocalBean
public class GitLabProvider implements IProvider {
	private static final Logger LOGGER=Logger.getLogger(GitLabProvider.class);
	
	@EJB
	private StartupBean configs;
	
	
	/* (non-Javadoc)
	 * @see pt.rest.api.provider.IProvider#getProviderType()
	 */
	@Override
	public ProviderType getProviderType() {
		return ProviderType.GITLAB;
	}


	/* (non-Javadoc)
	 * @see pt.rest.api.provider.IProvider#searchByQuery(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> searchByQuery(String query, int per_page, int page, String orderBy) {
		
		// TODO: Should be implemented!
		return null;
	}


	/* (non-Javadoc)
	 * @see pt.api.rest.provider.IProvider#searchAllByQuery(java.lang.String, int, int, java.lang.String)
	 */
	@Override
	public Response searchAllByQuery(String query, int per_page, int page, String orderBy) {
		
		// TODO: Should be implemented!
		return null;
	}

	
}