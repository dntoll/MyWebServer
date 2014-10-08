package se.lnu.http;

import static org.junit.Assert.*;

import org.junit.Test;

import se.lnu.http.exceptions.MalformedRequestException;


public class HTTPRequestParserTest {

	@Test
	public void testParseRequest() throws MalformedRequestException {
		String ok = "GET / HTTP/1.1\r\n";
			   ok += "Host: localhost:8080\r\n";
			   ok += "Connection: keep-alive\r\n";
			   ok += "Cache-Control: max-age=0\r\n";
			   ok += "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n";
			   ok += "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36\r\n";
			   ok += "Accept-Encoding: gzip,deflate,sdch\r\n";
			   ok += "Accept-Language: sv-SE,sv;q=0.8,en-US;q=0.6,en;q=0.4\"\r\n\r\n";
	    
		
		HTTPRequest actual = HTTPRequestParser.parseRequest(ok);
		
		
		assertEquals("/", actual.getURL());
	}
	
	@Test(expected=MalformedRequestException.class)
	public void testMalformedRequestEmpty() throws MalformedRequestException {
		String ok = "";
	    
		
		HTTPRequestParser.parseRequest(ok);
		
		
	}
	
	@Test(expected=MalformedRequestException.class)
	public void testMalformedRequest() throws MalformedRequestException {
		String ok = "/ HTTP/1.1\r\n";
		ok += "Host: localhost:8080\r\n";
		ok += "Connection: keep-alive\r\n";
			   
	    
		
		HTTPRequestParser.parseRequest(ok);
		
		
	}
	
	@Test(expected=MalformedRequestException.class)
	public void testMalformedRequestNoHost() throws MalformedRequestException {
		String ok = "GET / HTTP/1.1\r\n";
		ok += "Connection: keep-alive\r\n";
			   
	    
		
		HTTPRequestParser.parseRequest(ok);
		
		
	}
	
	@Test(expected=MalformedRequestException.class)
	public void testMalformedRequest2() throws MalformedRequestException {
		String ok = "";
		  	    
		
		HTTPRequestParser.parseRequest(ok);
		
		
	}

}
