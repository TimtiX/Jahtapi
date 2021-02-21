package de.jahtapi.http;

/**
 * An enumeration of different possible HTTP status codes.
 * 
 * @author Timti X
 */
public enum HttpStatus {

	OK("200 OK"),
	NOT_FOUND("404 Not Found"),
	INTERNAL_SERVER_ERROR("500 Internal Server Error");
	
	private String value;
	
	/**
	 * Creates the constant with a representing String value.
	 * 
	 * @param value String The value to represent this status code.
	 */
	private HttpStatus(String value) {
		this.value = value;
	}
	
	/**
	 * Returns the String value/representation of this status code.
	 * 
	 * @return String The String value.
	 */
	@Override
	public String toString() {
		return value;
	}
}
