package de.jahtapi.network;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class represents a server in the common sense. It runs on a given port and accepts
 * incoming connections, which will be handled by a ServerListener.
 * 
 * @author Timti X
 */
public final class Server {

	private ServerSocket socket;
	private ServerListener listener;
	private Thread handlerThread;
	
	/**
	 * Creates and starts the Server. 
	 * 
	 * @param port int The port to start the Server on.
	 * @param listener ServerListener The listener that handles incoming connections.
	 * @throws NullPointerExceion If the listener is null.
	 * @throws ServerCreationException If the Server could not be created for any reason.
	 */
	public Server(int port, ServerListener listener) {
		if(listener == null)
			throw new NullPointerException("The listener is null");
	
		this.listener = listener;
		
		try {
			socket = new ServerSocket(port);
			handlerThread = new Thread(new ServerHandlerRunnable(this));
			handlerThread.start();
		} catch(Exception exception) {
			throw new ServerCreationException(exception.getMessage());
		}
	}
	
	/**
	 * Stops the Server.
	 */
	public synchronized void close() {
		try {
			socket.close();
		} catch(Exception exception) {
			
		}
	}
	
	/**
	 * Returns if the Server is still open.
	 * 
	 * @return boolean The Servers open status.
	 */
	public synchronized boolean isOpen() {
		return !socket.isClosed();
	}
	
	/**
	 * This class is running on the Servers handler thread. It accepts incoming connections and
	 * passes them to the ServerListener as long as the Server is open. Should the thread encounter
	 * any exception it will stop without notice.
	 * 
	 * @author Timti X
	 */
	private static class ServerHandlerRunnable implements Runnable {
		
		private Server server;
		
		/**
		 * Creates a handler instance for the given Server.
		 * 
		 * @param server Server The Server this instance should handle.
		 */
		private ServerHandlerRunnable(Server server) {
			this.server = server;
		}
		
		/**
		 * The code that handles the Server. Runs as long as the Server is open and will terminate
		 * only if a Server side exception occurs or when the Server gets closed. Any exception
		 * while passing the incoming connection to the ServerListener will not lead to a
		 * termination but still prints a stack trace.
		 */
		@Override
		public void run() {
			try {
				while(server.isOpen()) {
					Socket socket = server.socket.accept();
					
					try {
						server.listener.onClientConnect(server, socket);
					} catch(Exception exception) {
						exception.printStackTrace();
					}
				}
			} catch(Exception exception) {
				
			}
		}
	}
}
