package uk.co.inetria.graphviz;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Servlet implementation class GraphvizServlet
 */
public class GraphvizServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(GraphvizServlet.class.getName());
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.info("Generating graph for dot");
		
		// read environment variable on how to get the backend host IP
		String value = System.getenv("GET_HOSTS_FROM");
		String urlStr;
		
		if("dns".equals(value)) {
			// Kubernetes should resolve the backend-service to an IP Address
			urlStr = "http://backend-service/svg";
			
		} else {
			// otherwise use this URL
			urlStr = "http://108.59.83.98:8080/svg";
		}
		
		log.info("Using url: " + urlStr);
		
		// the url and port number of the graphviz-server
		URL url = new URL(urlStr);

		String dot = request.getParameter("dot");

		response.setContentType("application/svg+xml");

		if(StringUtils.isNotBlank(dot)) {

			try {
				
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");

				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(dot);
				writer.close();

				// OK
				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					
					// RESPONSE
					byte[] content = IOUtils.toByteArray(new BufferedInputStream(connection.getInputStream()));
					
					if(content.length > 0) {
						response.getOutputStream().write(content);
						response.flushBuffer();
						
					} else {
						response.getWriter().print("<svg></svg>");
					}
			        
				} else {
					// Server returned HTTP error code.
					throw new Exception("Server returned HTTP error code");
				}
				
				connection.disconnect();
				
			} catch (Exception e) {
				log.log(Level.SEVERE, "Failed to generate graph", e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

		} else {
			response.getWriter().print("<svg></svg>");
		}
	}

}
