package de.jahtapi.http;

import java.util.HashMap;

/**
 * Resembles a HttpPacket with universal routines for it.
 * 
 * @author Timti X
 */
public class HttpPacket {
	
	private HashMap<String, String> headerFields;
	private String content;
	
	/**
	 * Initializes the empty HttpPacket.
	 */
	public HttpPacket() {
		headerFields = new HashMap<>();
		setContent("");
	}
	
	/**
	 * Sets a packet header field. If either the name or value are null nothing happens.
	 * @param name String The fields name.
	 * @param value String The fields value.
	 */
	public void setHeaderField(String name, String value) {
		if(name != null && value != null)
			headerFields.put(name, value);
	}
	
	/**
	 * Sets the packets content and updates the content-length header field accordingly. Nothing
	 * happens if the content is null.
	 *
	 * @param content String The new content/body.
	 */
	public void setContent(String content) {
		if(content != null) {
			this.content = content;
			headerFields.put("Content-Length", "" + content.length());
		}
	}
	
	/**
	 * Returns a header fields value. If it does not exist null is returned.
	 * 
	 * @param name String The field name.
	 * @return String The value or null.
	 */
	public String getHeaderField(String name) {
		return headerFields.get(name);
	}
	
	/**
	 * Returns the packets content.
	 * 
	 * @return String The packets content.
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Returns a String representation of this packet. Might not be a complete valid HTTP pacet, as
	 * there are no specifications of request/response etc.
	 * 
	 * @return String The String representation.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for(String headerField : headerFields.keySet())
			builder.append(headerField + ": " + headerFields.get(headerField) + "\r\n");
		
		builder.append("\r\n" + content);
		
		return builder.toString();
	}
}
