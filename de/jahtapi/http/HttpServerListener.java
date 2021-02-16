package de.jahtapi.http;

/**
 * The ability to listen for HttpServer events and to handle them accordingly.
 * 
 * @author Timti X
 */
public interface HttpServerListener {
	
	/**
	 * Called when a client connects.
	 * 
	 * @param client HttpClient The client that connected.
	 */
	void onClientConnect(HttpClient client);
	
	/**
	 * Called when a client closed either by themselves or by the HttpServer.
	 * 
	 * @param client HttpClient The client that disconnected.
	 */
	void onClientDisconnect(HttpClient client);
	
	/**
	 * Called when a client has new incoming packets.
	 * 
	 * @param client HttpClient The calling client.
	 * @param packet HttpPacket The incoming packet.
	 */
	void onClientRequest(HttpClient client, HttpPacket packet);
}
