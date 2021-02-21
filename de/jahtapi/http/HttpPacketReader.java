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
	 * Initializes the reader. It may only be used for one packet.
	 */
	public HttpPacketReader() {
		lines = new ArrayList<>();
		lineBuilder = new StringBuilder();
		checkLineFeed = false;
	}
	
	/**
	 * Input a new char. If a full packet was already read, nothing happens.
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
		} else {
			checkLineFeed = false;
			lineBuilder.append(c);
		}		
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
	 * Returns the new HttpPacket if available. Null is returned otherwise.
	 * 
	 * @return HttpPacket The read HttpPacket or null.
	 */
	public HttpPacket getPacket() {
		if(lines.size() > 0) {
			String firstLine = lines.get(0);
			String[] firstLineSplit = firstLine.split("\\ ");
			
			if(firstLine.startsWith("HTTP")) {
				HttpResponse response = new HttpResponse();
				
				if(firstLineSplit.length > 1) {
					response.setVersion(firstLineSplit[0]);
					response.setStatus(firstLine.substring("HTTP ".length()));
				}
				
				for(int index = 1; index < lines.size(); index++) {
					String[] headerLineSplit = lines.get(index).split("\\:\\ ");
					
					if(headerLineSplit.length > 1)
						response.setHeaderField(headerLineSplit[0], headerLineSplit[1]);
				}
				
				return response;
			} else {
				HttpRequest request = new HttpRequest();
				
				if(firstLineSplit.length == 3) {
					request.setMethod(firstLineSplit[0]);
					request.setPath(firstLineSplit[1]);
					request.setVersion(firstLineSplit[2]);
				}
				
				for(int index = 1; index < lines.size(); index++) {
					String[] headerLineSplit = lines.get(index).split("\\:\\ ");
					
					if(headerLineSplit.length > 1)
						request.setHeaderField(headerLineSplit[0], headerLineSplit[1]);
				}
				
				return request;
			}
		} else
			return null;
	}
}
