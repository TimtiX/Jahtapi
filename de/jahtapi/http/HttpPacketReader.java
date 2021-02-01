package de.jahtapi.http;

import java.util.ArrayList;

/**
 * Provides the utility to read packages from char streams.
 * 
 * @author Timti X
 */
public class HttpPacketReader {

	private ArrayList<String> lines;
	private StringBuilder lineBuilder;
	private boolean checkLineFeed;
	private boolean packetReady;
	
	/**
	 * Initializes the Reader. It may only be used for on packet.
	 */
	public HttpPacketReader() {
		lines = new ArrayList<>();
		lineBuilder = new StringBuilder();
		checkLineFeed = false;
	}
	
	/**
	 * Input a new char. If a full packet was already read nothing happens.
	 * 
	 * !WARNING! Not finished, just reads the header lines.
	 * 
	 * @param c char The next char to read.
	 */
	public void input(char c) {
		if(c == '\r' || packetReady)
			return;
		
		if(c == '\n') {
			if(checkLineFeed) {
				packetReady = true;
				checkLineFeed = false;
			} else {
				lines.add(lineBuilder.toString());
				lineBuilder = new StringBuilder();
				checkLineFeed = true;
			}
		} else
			checkLineFeed = false;
			
		lineBuilder.append(c);
	}
	
	/**
	 * Returns if a full packet was read and is available.
	 * 
	 * @return boolean If a packet is ready to be processed.
	 */
	public boolean isPacketReady() {
		return packetReady;
	}
	
	/**
	 * Returns the packets header fields.
	 * 
	 * @return ArrayList<String> The packets header fields.
	 */
	public ArrayList<String> getPacket() {
		return lines;
	}
}
