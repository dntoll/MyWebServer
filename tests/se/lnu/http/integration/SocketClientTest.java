package se.lnu.http.integration;

import java.net.Socket;
import org.junit.Test;

import static org.junit.Assert.*;

import se.lnu.http.client.HTTPGetProtocoll;
import se.lnu.http.client.SocketClient;


public class SocketClientTest {

	
	/* This test requires both online server and access to internet
	 * */
	@Test
	public void testGetFromOnlineServer() throws Exception {
		
		SocketClient sut = new SocketClient(new Socket(), new HTTPGetProtocoll());
		String actual = sut.get("194.47.172.159", 80, "/");
		
		String expected[] = new String[7];
		expected[0] = "HTTP/1.1 200 OK\r\n";
		expected[1] = "Date: ";
		expected[2] = "Server: Apache/2.2.17 (Win32) mod_wsgi/3.3 Python/2.6.6\r\n";
		expected[3] = "Accept-Ranges: bytes\r\n";
		expected[4] = "Content-Length: 44\r\n";
		expected[5] = "Content-Type: text/html\r\n";
		expected[6] = "\r\n";
		
		for(String exp : expected) {
			assertTrue(actual.contains(exp));
		}
		
	}
	
	
}
