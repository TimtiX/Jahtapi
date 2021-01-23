package de.jahtapi.network;

import java.net.Socket;

/**
 * This interface provides the ability to handle incoming connection from a Server.
 * 
 * @author Timti X
 */
public interface ServerListener {
	
	/**
	 * Called to handle an incoming connection from a Server this listener is assigned to.
	 * 
	 * @param server Server The Server that this connection happened on.
	 * @param socket Socket The Socket of this connection.
	 */
	void onClientConnect(Server server, Socket socket);
}
