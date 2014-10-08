package se.lnu.http;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.lnu.http.response.HTTP200OKFileResponse;
import se.lnu.http.response.HTTP403Forbidden;
import se.lnu.http.response.HTTP404FileNotFoundResponse;
import se.lnu.http.response.HTTP405MethodNotSupportedResponse;
import se.lnu.http.response.HTTPResponse;

public class ResponseFactoryTest {

	private ResponseFactory sut;
	private SharedFolder folder;
	private HTTPRequest request;

	@Before
	public void setUp() throws Exception {
		folder = mock(SharedFolder.class);
		IServerWatcher watcher = mock(IServerWatcher.class);
		sut = new ResponseFactory(folder, watcher, 1);
		request = mock(HTTPRequest.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	

	@Test
	public void testGetResponseGETRoot() throws IOException {
		
		when(request.getMethod()).thenReturn(HTTPRequest.Method.GET);
		when(request.getURL()).thenReturn("/");
		
		HTTPResponse resp = sut.getResponse(request);
		
		assertTrue(resp.getClass() == HTTP200OKFileResponse.class);
	}
	
	@Test
	public void testGetResponseUnexistingFile() throws IOException {
		
		when(request.getMethod()).thenReturn(HTTPRequest.Method.GET);
		when(request.getURL()).thenReturn("/crap.html");
		
		when(folder.getURL(anyString())).thenThrow(new FileNotFoundException("/crap.html"));
		
		HTTPResponse resp = sut.getResponse(request);
		
		assertTrue(resp.getClass() == HTTP404FileNotFoundResponse.class);
	}
	
	@Test
	public void testUnknownMethod() throws IOException {
		
		when(request.getMethod()).thenReturn(HTTPRequest.Method.DELETE);
		when(request.getURL()).thenReturn("/crap.html");
		
		when(folder.getURL("/crap.html")).thenThrow(new FileNotFoundException("/crap.html"));
		
		HTTPResponse resp = sut.getResponse(request);
		
		assertTrue(resp.getClass() == HTTP405MethodNotSupportedResponse.class);
	}
	
	@Test
	public void testIllegalFile() throws IOException {
		
		when(request.getMethod()).thenReturn(HTTPRequest.Method.GET);
		when(request.getURL()).thenReturn("/../crap.html");
		
		when(folder.getURL("/../crap.html")).thenThrow(new SecurityException("/crap.html"));
		
		HTTPResponse resp = sut.getResponse(request);
		
		assertTrue(resp.getClass() == HTTP403Forbidden.class);
	}
	
	@Test
	public void getBad() throws IOException {
		
		assertNotNull(sut.getBadResponse());
	}

}
