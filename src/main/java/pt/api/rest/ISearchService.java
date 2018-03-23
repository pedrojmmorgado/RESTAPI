package pt.api.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Search REST API. 
 * @author PMorgado
 *
 */
@Path("/restapi")
public interface ISearchService {


	/**
	 * Search by query.
	 * @param query 
	 * @param per_page - The number of hits per page should be 25 by default, but must be changeable by a query string parameter
	 * @param page - The page number should be changeable by a query string parameter
	 * @param orderBy -  The sorting should be by score, but must be changeable by a query string parameter
	 * @return
	 */
	@GET
	@Path("/search/{query}")
	@Produces("application/json;charset=utf-8")
	List<Map<String, Object>> searchByQuery(
			@PathParam("query") String query,
			@DefaultValue("25") @QueryParam("per_page") int per_page,
			@DefaultValue("-1") @QueryParam("page") int page,
			@DefaultValue("score") @QueryParam("orderBy") String orderBy);

}