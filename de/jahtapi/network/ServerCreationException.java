package de.jahtapi.network;

/**
 * A RuntimeException that should be thrown when a Server of any kind fails to be created.
 * 
 * @author Timti X
 */
public class ServerCreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates the exception with a given error message.
	 * 
	 * @param message String The error message to show.
	 */
	public ServerCreationException(String message) {
		super(message);
	}
	
	/**
	 * Creates the exception with the hint that no further info for the exception is given.
	 */
	public ServerCreationException() {
		this("No further info");
	}
}
