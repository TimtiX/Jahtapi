package de.jahtapi.http;

/**
 * The ability to handle incoming HTTP requests from a HttpClient.
 * 
 * @author Timti X
 */
public interface HttpClientListener {
	
	/**
	 * Called by the HttpClient this Listener was assigned to when a complete HTTP Request was read
	 * after or during a receive() procedure.
	 * 
	 * @param client HttpClient The Client this event is coming from.
	 * @param message String The message that was sent by it.
	 */
	void onHttpRequest(HttpClient client, String message);
}
