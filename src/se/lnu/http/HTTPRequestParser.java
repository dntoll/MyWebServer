package se.lnu.http;

import java.util.HashMap;
import java.util.Map;

import se.lnu.http.exceptions.MalformedRequestException;

public class HTTPRequestParser {

	public static HTTPRequest parseRequest(String requestString) throws MalformedRequestException {
		
		String[] lines = requestString.split("\r\n");
		
		String firstLine = lines[0];
		String[] requestType = firstLine.split(" ");
		if (requestType.length != 3) {
			throw new MalformedRequestException(requestString);
		}
		
		HTTPRequest.Method method = HTTPRequest.Method.fromString(requestType[0]);
		
		Map<Header.HTTPHeader,Header> headers = new HashMap<Header.HTTPHeader,Header>();
		for (int i = 1; i < lines.length; i++) {
			Header h = (Header.fromString(lines[i]));
			headers.put(h.getType(), h);
		}
		
		return new HTTPRequest(method, requestType[1], headers);
	}


}
