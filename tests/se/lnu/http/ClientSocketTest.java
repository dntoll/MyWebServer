package se.lnu.http;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClientSocketTest {

	private Socket socket;
	private ClientSocket sut;
	String expected = "some data\r\nother stuff\r\n\r\n";

	@Before
	public void setUp() throws Exception {
		socket = mock(Socket.class);
		sut = new ClientSocket(socket);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetRequest() throws IOException {
		
		
		
		ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(expected.getBytes());
		when(socket.getInputStream()).thenReturn( byteArrayStream);
		
		String actual = sut.getRequest(1000);
		assertEquals(expected, actual);
	}

	@Test
	public void testWriteResponseHeader() throws IOException {
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		when(socket.getOutputStream()).thenReturn( outContent);
		sut.writeHeader(expected);
		
		assertEquals(expected, outContent.toString());
		
		verify(socket).getOutputStream();
	}
	
	@Test
	public void testWriteResponseBody() throws IOException {
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		when(socket.getOutputStream()).thenReturn( outContent);
		sut.writeBody(expected.getBytes());
		
		for (int i = 0; i < expected.getBytes().length; i++) {
			assertEquals(expected.getBytes()[i], outContent.toByteArray()[i]);
		}
		
		verify(socket).getOutputStream();
	}

}
