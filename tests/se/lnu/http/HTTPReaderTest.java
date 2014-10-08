package se.lnu.http;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class HTTPReaderTest {

	private BufferedReader reader;
	private HTTPReader sut;

	
	@Before
	public void setUp() throws Exception {
		reader = mock(BufferedReader.class);
		sut = new HTTPReader(reader);
		
	}
	
	@Test
	public void testReadOk() throws IOException {
		String expected = "foo\r\nfoo\r\n\r\n";
		when(reader.readLine()).thenReturn("foo").thenReturn("foo").thenReturn("").thenReturn("");
		String actual = sut.readAll();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testReadBody() throws IOException {
		String expected = "foo\r\nContent-Length: 3\r\n\r\nabc";
		when(reader.readLine()).thenReturn("foo").thenReturn("Content-Length: 3").thenReturn("").thenReturn("");
		when(reader.read()).thenReturn((int)'a').thenReturn((int)'b').thenReturn((int)'c');
		String actual = sut.readAll();
		
		assertEquals(expected, actual);
	}
	
	@Test(expected=IOException.class)
	public void testbroken() throws IOException {
		when(reader.readLine()).thenThrow(new IOException());
		sut.readAll();
		
	}
	
	@Test(expected=IOException.class)
	public void testbroken2() throws IOException {
		when(reader.readLine()).thenReturn("foo").thenReturn("Content-Length: 3").thenReturn("").thenReturn("");
		when(reader.read()).thenThrow(new IOException());
		sut.readAll();
		
	}

}
