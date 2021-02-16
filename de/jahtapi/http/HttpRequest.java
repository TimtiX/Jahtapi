package de.jahtapi.http;

/**
 * An extension of the HttpPacket with request specific operations.
 * 
 * @author Timti X
 */
public class HttpRequest extends HttpPacket {

	private String version;
	private String method;
	private String path;
	
	/**
	 * Initializes the HttpRequest with default values.
	 */
	public HttpRequest() {
		super();
		version = "HTTP/1.1";
		method = "GET";
		path = "/";
	}
	
	/**
	 * Sets the packets HTTP version. If the String is null nothing changes.
	 * 
	 * @param version String The new version.
	 */
	public void setVersion(String version) {
		if(version != null)
			this.version = version;
	}
	
	/**
	 * Sets the request method. If the String is null nothing changes.
	 * 
	 * @param method String The new method of this request.
	 */
	public void setMethod(String method) {
		if(method != null)
			this.method = method;
	}
	
	/**
	 * Sets the requested path. If the String is null nothing changes.
	 * 
	 * @param path String The new path to request.
	 */
	public void setPath(String path) {
		if(path != null)
			this.path = path;
	}
	
	/**
	 * Returns the packets HTTP version.
	 * 
	 * @return String The packets version.
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * Returns the response method.
	 * 
	 * @return String The method.
	 */
	public String getMethod() {
		return method;
	}
	
	/**
	 * Returns the requested path.
	 * 
	 * @return String The requested path.
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Returns a String representation of this HttpRequest, which for example may be send via a
	 * HttpClient.
	 * 
	 * @return String The String representation.
	 */
	@Override
	public String toString() {
		return method + " " + path + " " + version + "\r\n" + super.toString();
	}
}
