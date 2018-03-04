package pt.api.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Search REST API. 
 * @author PMorgado
 *
 */
@Path("/restapi")
public interface ISearchService {


	/**
	 * Search by query.
	 */
	@GET
	@Path("/search/{query}")
	@Produces("application/json;charset=utf-8")
	List<Map<String, Object>> searchByQuery(@PathParam("query") String query);

}