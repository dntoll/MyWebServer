package se.lnu.http;

import java.util.Map;

import se.lnu.http.exceptions.*;

public class HTTPRequest {
	//http://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol
	public enum Method {
	    GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, CONNECT, PATCH;
	    
	    static Method fromString(String stringMethod) throws MalformedRequestException {
	    	for(Method m : Method.values()) {
	    		if (stringMethod.equals(m.name())) {
	    			return m;
	    		}
	    	}
	    	throw new MalformedRequestException("unknown method ["+stringMethod+"]");
	    	
	    }
	}
	
	private String url;
	private Method requestType;
	private Map<Header.HTTPHeader, Header> headers;

	public HTTPRequest(Method requestType, String url, Map<Header.HTTPHeader, Header> headers) throws MalformedRequestException {
		this.url = url;
		this.requestType = requestType;
		this.headers = headers;
		
		
		if (headers.containsKey(Header.HTTPHeader.Host) == false) {
			throw new MalformedRequestException("no header found");
		}
	}

	public String getURL() {
		return this.url;
	}

	public Method getMethod() {
		return requestType;
	}

	public boolean doCloseAfterResponse() {
		
		if ( headers.containsKey(Header.HTTPHeader.Connection) ) {
			if (headers.get(Header.HTTPHeader.Connection).getValue().contains("close")) {
				return true;
			}
		}
		return false;
	}

}
