package de.jahtapi.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A Socket handler for incoming and outgoing HTTP traffic.
 * 
 * @author Timti X
 */
public class HttpClient {

	private static final int DEFAULT_MAX_BYTES = 1024;
	
	private Socket socket;
	private HttpClientListener listener;
	private Object threadLock;
	private HttpPacketReader reader;
	private long lastDataReadTime;

	/**
	 * Initializes the client for a Socket that gets handled and a listener to relay events to.
	 * 
	 * @param socket The Socket connection to handle.
	 * @param listener The listener to callback to.
	 * @throws NullPOinterException If a parameter is null.
	 */
	public HttpClient(Socket socket, HttpClientListener listener) {
		if(socket == null || listener == null)
			throw new NullPointerException("Socket or listener is null");
		
		this.socket = socket;
		this.listener = listener;
		threadLock = new Object();
		reader = new HttpPacketReader();
		lastDataReadTime = System.currentTimeMillis();
	}
	
	/**
	 * Sends a given message to the connected device. Nothing happens if the message is null.
	 * 
	 * @param message String The message to send.
	 */
	public void send(String message) {
		if(message == null)
			return;
		
		synchronized(threadLock) {
			try {
				OutputStream outputStream = socket.getOutputStream();
		
				for(int index = 0; index < message.length(); index++)
					outputStream.write(message.charAt(index));
				
				outputStream.flush();
			} catch(Exception exception) {
				close();
			}
		}
	}
	
	/**
	 * Reads a given maximum of bytes of input data sent from the connected device. Data will only
	 * be read if it is available.
	 * 
	 * @param maxBytes int The maximum amount of bytes to read.
	 */
	public void receive(int maxBytes) {
		ArrayList<HttpPacket> inputQueue = new ArrayList<>();
		
		synchronized(threadLock) {
			try {
				InputStream inputStream = socket.getInputStream();
				
				int count;
				for(count = 0; count < maxBytes && inputStream.available() > 0; count++) {
					char input = (char) inputStream.read();	
					reader.input(input);
					
					if(reader.isPacketReady()) {
						inputQueue.add(reader.getPacket());
						reader = new HttpPacketReader();
					}
				}
				
				if(count > 0)
					lastDataReadTime = System.currentTimeMillis();
			} catch(Exception exception) {
				close();
			}
		}
		
		while(inputQueue.size() > 0) {
			listener.onHttpRequest(this, inputQueue.get(0));
			inputQueue.remove(0);
		}
	}
	
	/**
	 * Invokes the receive() method with a default amount of max bytes.
	 */
	public void recieve() {
		receive(DEFAULT_MAX_BYTES);
	}
	
	/**
	 * Closes the clients connection.
	 */
	public void close() {
		try {
			socket.close();
		} catch(Exception exception) {
			
		}
	}
	
	/**
	 * Returns if the client was closed or not.
	 * 
	 * @return boolean If the client was closed.
	 */
	public boolean isClosed() {
		return socket.isClosed();
	}
	
	/**
	 * Returns the last time that data was read/received. Could be used to determine timeouts.
	 * 
	 * @return long The last time data was read.
	 */
	public long getLastDataReadTime() {
		return lastDataReadTime;
	}
}
