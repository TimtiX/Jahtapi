package de.jahtapi.http;

/**
 * The ability to handle incoming HTTP packets from a HttpClient.
 * 
 * @author Timti X
 */
public interface HttpClientListener {
	
	/**
	 * Called by the HttpClient this Listener was assigned to when a complete HTTP Packet was read
	 * after or during a receive() procedure.
	 * 
	 * @param client HttpClient The Client this event is coming from.
	 * @param packet HttpPacket The packet that was sent by the Client.
	 */
	void onHttpRequest(HttpClient client, HttpPacket packet);
}
