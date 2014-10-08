package se.lnu.http.response;

import java.io.IOException;

import se.lnu.http.ClientSocket;

public abstract class HTTPResponse {

	public abstract void writeResponse(ClientSocket clientSocket, boolean doContinue) throws IOException;
	
	protected String writeContentLengthAndEndHeader(long length, ContentType type, boolean doContinue) {
		String ret = "";
		ret += ("Content-Type: " + type.toString() + "\r\n");
		ret += ("Content-Length: " + length + "\r\n");
		
		if (doContinue == false) {
			ret += ("Connection: close\r\n");
		}
		
		
		ret += ("\r\n");
		
		
		return ret;
	}

}
