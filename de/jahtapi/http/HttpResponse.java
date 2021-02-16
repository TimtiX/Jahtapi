package de.jahtapi.http;

/**
 * An extension of a HttpPacket supporting response specific operations.
 * 
 * @author Timti X
 */
public class HttpResponse extends HttpPacket {

	private String version;
	private String status;
	
	/**
	 * Initializes the HttpResponse with default values.
	 */
	public HttpResponse() {
		super();
		version = "HTTP/1.1";
		status = "200 OK";
	}
	
	/**
	 * Updates the packets HTTP version. If the String is null nothing changes.
	 * 
	 * @param version String The new version.
	 */
	public void setVersion(String version) {
		if(version != null)
			this.version = version;
	}
	
	/**
	 * Sets the response status code. If the String is null nothing changes.
	 * 
	 * @param status String The new status code.
	 */
	public void setStatus(String status) {
		if(status != null)
			this.status = status;
	}
	
	/**
	 * Returns the packets HTTP version.
	 * 
	 * @return String The HTTP version.
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * Returns the responses status code.
	 * 
	 * @return String The status code.
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Returns a String representation of the whole packet. This can be for example send via a 
	 * HttpClient.
	 * 
	 * @return String The String representation.
	 */
	@Override
	public String toString() {
		return version + " " + status + "\r\n" + super.toString();
	}
}
