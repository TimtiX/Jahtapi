package de.jahtapi.http;

/**
 * The ability to Listen for a HttpServers events and to handle them accordingly.
 * 
 * @author Timi X
 */
public interface HttpServerListener {
	
	/**
	 * Called when a Client connects.
	 * 
	 * @param client HttpClient The Client that connected.
	 */
	void onClientConnect(HttpClient client);
	
	/**
	 * Called when a Client closed either by themselves or by the Server-
	 * 
	 * @param client HttpClient The Client that disconnected.
	 */
	void onClientDisconnect(HttpClient client);
	
	/**
	 * Called when a Client has new incoming requests.
	 * 
	 * @param client HttpClient The Client calling.
	 * @param message String The incoming request.
	 */
	void onClientRequest(HttpClient client, HttpPacket packet);
}
