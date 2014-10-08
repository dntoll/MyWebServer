package se.lnu.http;


import se.lnu.http.exceptions.MalformedRequestException;

public class Header {
	
	public enum HTTPHeader {
	    Host("Host"), 
	    ContentType("Content-Type"), 
	    ContentLength("Content-Length"), 
	    Connection("Connection"),
	    CacheControl("Cache-Control"),
	    Accept("Accept"),
	    UserAgent("User-Agent"),
	    AcceptEncoding("Accept-Encoding"),
	    AcceptLanguage("Accept-Language"),
	    UnknownHeader("Unknown-Header");
	    
	    String headerText;
	    private HTTPHeader(String headerText) {
	    	this.headerText = headerText;
	    }
	    
	    
	}

	private String value;
	private HTTPHeader type;
	
	public Header(HTTPHeader type, String value) {
		this.value = value;
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}

	public HTTPHeader getType() {
		return type;
	}

	static Header fromString(String stringHeader) throws MalformedRequestException {
		String[] parts = stringHeader.split(": ");
    	if (parts.length != 2) {
    		throw new MalformedRequestException("unknown format of Header ["+stringHeader+"]");
    	}
		
		for(HTTPHeader m : HTTPHeader.values()) {
    		if (stringHeader.startsWith(m.headerText)) {
    			return new Header(m, parts[1]);
    		}
    	}
    	
    	
    	//
    	return new Header(HTTPHeader.UnknownHeader, parts[1]);
    }

	
	
}
