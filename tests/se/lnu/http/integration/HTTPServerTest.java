package se.lnu.http.integration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.lnu.http.HTTPServer;
import se.lnu.http.IServerWatcher;
import se.lnu.http.Port;
import se.lnu.http.SharedFolder;
import se.lnu.http.client.HTTPGetProtocoll;
import se.lnu.http.client.SocketClient;
import se.lnu.http.exceptions.InvalidPortException;
import se.lnu.http.exceptions.NotStartedException;


public class HTTPServerTest {

	IServerWatcher observer;
	HTTPServer sut;
	private int portNumber = 8088;
	SharedFolder sharedDirectory = new SharedFolder(new File("/"));
	
	@Before
	public void setUp() throws Exception {
		observer = mock(IServerWatcher.class);
		
		sut = new HTTPServer(new Port(portNumber), sharedDirectory, observer);
	}

	@After
	public void tearDown() throws Exception {
		
		try {
			sut.stop();
		} catch(NotStartedException e) {
			//ok
		}
	}

	@Test
	public void testHTTPServer() throws InvalidPortException {
		verify(observer).serverConstructed();
	}

	@Test
	public void testStart() throws IOException, NotStartedException, InterruptedException {
		verify(observer).serverConstructed();
		sut.start();
		verify(observer).serverStarted();
		sut.stop();
	}

	@Test
	public void testStop() throws NotStartedException, IOException, InterruptedException {
		
		verify(observer).serverConstructed();
		assertFalse(serverIsOnline());
		sut.start();
		
		assertTrue(serverIsOnline());
		
		sut.stop();
		
		assertFalse(serverIsOnline());
		verify(observer).serverStopped();
	}

	private boolean serverIsOnline() {
		try {
			SocketClient client = new SocketClient(new Socket(), new HTTPGetProtocoll());
			String actual = client.get("127.0.0.1", portNumber , "/crap.php");
			String response = "HTTP/1.1 404 Not Found\r\n";
			
			return actual.contains(response);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return false;
	}
	
	@Test
	public void testStopWhenNotStarted() throws InterruptedException, IOException {
		verify(observer).serverConstructed();
		try {
			sut.stop();
			fail();
		} catch(NotStartedException e) {
			//ok
		}
		verify(observer, never()).serverStopped();
		
	}

}
