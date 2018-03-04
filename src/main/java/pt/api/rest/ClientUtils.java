package pt.api.rest;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

/**
 * Common utils.
 * @author PMorgado
 *
 */
public final class ClientUtils {
	
	private static final int HTTP_STATUS_SUCCESS = 200;
	private static final String EXCEPTION_KEY = "exception";
	private static final String MESSAGE_KEY = "message";
	
	private ClientUtils() {
	}
	
	public static Object getResponseEntity(String service, Response resp, Logger logger) throws ClientException {
		
		logger.tracev("{0}:respCode: {1} {2} {3}", service, resp.getStatus(), resp.getStatusInfo().getStatusCode(), resp.getStatusInfo().getReasonPhrase());
		
		if (resp.getStatus() != HTTP_STATUS_SUCCESS) {
			logger.errorv("{0}:WrongStatus: {1} {1}", service, resp.getStatus(), resp.getStatusInfo() != null ? resp.getStatusInfo().getReasonPhrase() : "");
			throw new ClientException();
		}
		
		Object e=resp.readEntity(Object.class);
		
		if (!(e instanceof Map)) {
			logger.errorv("{0}:WrongType: {0}", service, e != null ? e.getClass() : "<null>");
			throw new ClientException();
		}
		
		if (((Map<String,Object>)e).containsKey(EXCEPTION_KEY)) {
			logger.errorv("{0}:Exception:{1}: {2}", service, ((Map<String,Object>)e).get(EXCEPTION_KEY),((Map<String,Object>)e).get(MESSAGE_KEY));
			throw new ClientException();
		}
		
		return e;
	}
}
