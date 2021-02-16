package de.jahtapi.http;

/**
 * The ability to handle incoming HTTP packets from a HttpClient.
 * 
 * @author Timti X
 */
public interface HttpClientListener {
	
	/**
	 * Called by the HttpClient this listener was assigned to when a complete HttpPacket was read
	 * after or during a receive() procedure.
	 * 
	 * @param client HttpClient The client this event is coming from.
	 * @param packet HttpPacket The packet that was sent by the client.
	 */
	void onHttpRequest(HttpClient client, HttpPacket packet);
}
