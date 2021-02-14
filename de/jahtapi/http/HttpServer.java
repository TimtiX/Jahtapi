package de.jahtapi.http;

import java.net.Socket;
import java.util.ArrayList;

import de.jahtapi.network.Server;
import de.jahtapi.network.ServerCreationException;
import de.jahtapi.network.ServerListener;

/**
 * Represents a HttpServer in the common sense. It accepts and handles Clients and relays incoming
 * traffic to a Listener.
 * 
 * @author Timti X
 */
public class HttpServer {

	private static final int HTTP_DEFAULT_PORT = 80;
	
	private Server server;
	private HttpServerListener listener;
	private Thread handlerThread;
	private Object threadLock;
	private ArrayList<HttpClient> clients;
	
	/**
	 * Initializes the HttpServer for the given port and Listener.
	 * 
	 * @param port int The port to start on.
	 * @param listener HttpListener The Listener to callback to.
	 * @throws NullPointerException If a parameter is null.
	 * @throws ServerCreationException If the Server creation fails for unknown reasons.
	 */
	public HttpServer(int port, HttpServerListener listener) {
		if(listener == null)
			throw new NullPointerException("Listener is null");
		
		this.listener = listener;
		clients = new ArrayList<>();
		threadLock = new Object();
		
		try {
			server = new Server(port, new CustomServerListener(this));
			handlerThread = new Thread(new ServerHandleRunnable(this));
			handlerThread.start();
		} catch(Exception exception) {
			throw new ServerCreationException(exception.getMessage());
		}
	}
	
	/**
	 * Creates the HttpServer on a default port with the given Listener.
	 * 
	 * @param listener HttpListene The Listener to callback to.
	 * @throws NullPointerExcption If the Listener is null.
	 * @throws ServerCreationException If the Server creation fails for unknown reasons.
	 */
	public HttpServer(HttpServerListener listener) {
		this(HTTP_DEFAULT_PORT, listener);
	}

	/**
	 * Closes the Server.
	 */
	public void close() {
		server.close();
	}
	
	/**
	 * The ServerListener to handle the objects TCP Server.
	 * 
	 * @author Timti x
	 */
	private static class CustomServerListener implements ServerListener {

		private HttpServer server;
		
		/**
		 * Initializes this Listener for then given owner.
		 * 
		 * @param server HttpServer The HttpServer this object belongs to.
		 */
		private CustomServerListener(HttpServer server) {
			this.server = server;
		}
		
		/**
		 * Called by the TCP Server when a Client connects. Creates an HttpClient and relays the
		 * event to the HttpServers Listener.
		 * 
		 * @param server Server The Server this event is coming from.
		 * @param socket Socket The connection with the Client.
		 */
		@Override
		public void onClientConnect(Server server, Socket socket) {
			HttpClient client = new HttpClient(socket, new CustomClientListener(this.server));
			
			synchronized(this.server.threadLock) {
				try {
					this.server.listener.onClientConnect(client);
				} catch(Exception exception) {
					exception.printStackTrace();
				}
				
				this.server.clients.add(client);
			}
		}		
	}
	
	/**
	 * The ClientListener to listen for Clients input so it can be reayed to the HttpServers 
	 * Listener.
	 *
	 * @author Timti X
	 */
	private static class CustomClientListener implements HttpClientListener {

		private HttpServer server;
		
		/**
		 * Creates the Listener for the given owner.
		 * 
		 * @param server HttpServer The Server this Listener belongs to.
		 */
		private CustomClientListener(HttpServer server) {
			this.server = server;
		}
		
		/**
		 * Called when a Client has a new incoming request.
		 * 
		 * @param client HttpClient The Client the event comes from.
		 * @param packet HttpPacket The received packet.
		 */
		@Override
		public void onHttpRequest(HttpClient client, HttpPacket packet) {
			server.listener.onClientRequest(client, packet);
		}		
	}
	
	/**
	 * Handles the new and existing Clients of this HttpServer.
	 * 
	 * @author Timti X
	 */
	private static class ServerHandleRunnable implements Runnable {
		
		private HttpServer server;
		
		/**
		 * Creates this Runnable for the given owner.
		 * 
		 * @param server HttpServer The Server this Runnable belongs to.
		 */
		private ServerHandleRunnable(HttpServer server) {
			this.server = server;
		}
		
		/**
		 * Called by the Thread. It contains the handling code and manages new and disconnected
		 * Clients. If no Clients exist the routine tries to free processing power. If the Runnable
		 * ends it will close all Client connections.
		 */
		@Override
		public void run() {
			while(server.server.isOpen()) {
				long timeToSleep;
				
				synchronized(server.threadLock) {
					ArrayList<HttpClient> closedClients = new ArrayList<>();
					
					for(HttpClient client : server.clients)
						if(client.isClosed())
							closedClients.add(client);
						else
							client.recieve();
					
					for(HttpClient client : closedClients) {
						try {
							server.listener.onClientDisconnect(client);
						} catch(Exception exception) {
							exception.printStackTrace();
						}
						
						server.clients.remove(client);
					}
					
					timeToSleep = 100 - server.clients.size();
				}
				
				try {
					if(timeToSleep > 0)
						Thread.sleep(timeToSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			for(HttpClient client : server.clients) {
				client.close();
				server.listener.onClientDisconnect(client);
			}
		}
	}
}
