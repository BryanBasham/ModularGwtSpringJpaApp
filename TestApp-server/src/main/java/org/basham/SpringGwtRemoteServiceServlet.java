package org.basham;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * A Single GWT remote service servlet that dispatches to Spring Services.
 * 
 * TODO:
 *   1) Do Service beans really need the GWT {@link RemoteService} marker interface?
 *      I could code up a replacement to RPC.decodeRequest method to not require
 *      this but then do I sacrifice security?
 * 
 * @author <a href='mailto:basham47@gmail.com'>Bryan Basham</a>
 */
@SuppressWarnings("serial")
public class SpringGwtRemoteServiceServlet extends RemoteServiceServlet {
	private static final Logger LOG = Logger.getLogger(SpringGwtRemoteServiceServlet.class);

	private WebApplicationContext springCtx;
	
	@Override
	public void init() throws ServletException {
		springCtx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		if (springCtx == null) {
			throw new ServletException("No Spring web application context found");
		}
	}

	@Override
	public String processCall(String payload) throws SerializationException {
		try {
			Object handler = getBean(getThreadLocalRequest());
			RPCRequest rpcRequest = RPC.decodeRequest(payload, handler.getClass(), this);
			onAfterRequestDeserialized(rpcRequest);

			return RPC.invokeAndEncodeResponse(handler,
					rpcRequest.getMethod(),
					rpcRequest.getParameters(),
					rpcRequest.getSerializationPolicy());

		} catch (IncompatibleRemoteServiceException ex) {
			LOG.warn("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
			return RPC.encodeResponseForFailure(null, ex);
			
		} catch (final Throwable e) {
			final String message = "Unknown exception: " + e.getMessage();
			LOG.error(message, e);
			return RPC.encodeResponseForFailure(null, new RuntimeException(message));
		}
	}

	/*
	 * Determine Spring bean to handle request based on request URL, e.g. a
	 * request ending in /myService will be handled by bean with name
	 * "myService".
	 */
	private Object getBean(HttpServletRequest request) {
		// Determine the name of the Spring bean from the HTTP request
		String url = request.getRequestURI();
		String service = url.substring(url.lastIndexOf("/") + 1);
		if (LOG.isDebugEnabled()) {
			LOG.debug("Service for URL " + url + " is " + service);
		}
		
		// Get the Spring bean for this Service
		if (!springCtx.containsBean(service)) {
			throw new IllegalArgumentException("Spring bean not found: " + service);
		}
		Object bean = springCtx.getBean(service);
		
		// Validate that this bean is a GWT remote service
		if (!(bean instanceof RemoteService)) {
			throw new IllegalArgumentException(
					"Spring bean is not a GWT RemoteService: " + service + " ("
							+ bean + ")");
		}
		return bean;
	}
}
