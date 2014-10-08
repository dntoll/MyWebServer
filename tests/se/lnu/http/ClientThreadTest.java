package se.lnu.http;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.lnu.http.response.HTTP400BadRequest;
import se.lnu.http.response.HTTPResponse;

public class ClientThreadTest {

	private ClientSocket sock;
	private ResponseFactory factory;
	private ClientThread sut;
	private IServerWatcher watcher;

	@Before
	public void setUp() throws Exception {
		sock = mock(ClientSocket.class);
		factory = mock(ResponseFactory.class);
		watcher = mock(IServerWatcher.class);
		sut = new ClientThread(sock, factory, watcher, 1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRun() throws IOException {
		HTTPResponse response = mock(HTTPResponse.class);
		
		when(sock.getRequest(ClientThread.timeOutMilliseconds)).thenReturn("GET / HTTP1.1\r\nHost: host\r\nConnection: close\r\n\r\n");
		when(factory.getResponse(any(HTTPRequest.class))).thenReturn(response);
		
		sut.run();
		
		verify(response).writeResponse(sock, false);
	}
	
	@Test
	public void testMalformed() throws IOException {
		
		HTTP400BadRequest response = mock(HTTP400BadRequest.class);
		when(sock.getRequest(ClientThread.timeOutMilliseconds)).thenReturn("GET HTTP1.1\r\nHost: host\r\nConnection: close\r\n\r\n");
		
		when(factory.getBadResponse()).thenReturn(response);
		
		sut.run();
		
		verify(response).writeResponse(sock, false);
	}
	
	@Test
	public void testMultipleConnections() throws IOException {
		
		HTTPResponse response = mock(HTTPResponse.class);
		when(sock.getRequest(ClientThread.timeOutMilliseconds)).
			thenReturn("GET / HTTP1.1\r\nHost: host\r\n\r\n").
			thenReturn("GET / HTTP1.1\r\nHost: host\r\nConnection: close\r\n\r\n");
		
		when(factory.getResponse(any(HTTPRequest.class))).thenReturn(response);
		
		
		sut.run();
		
		verify(sock, times(2)).getRequest(ClientThread.timeOutMilliseconds);
	}
	
	
}
