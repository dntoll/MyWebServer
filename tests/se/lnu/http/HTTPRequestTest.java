package se.lnu.http;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.lnu.http.exceptions.MalformedRequestException;

public class HTTPRequestTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=MalformedRequestException.class)
	public void testGetURL() throws MalformedRequestException {
		HTTPRequest.Method.fromString("ddd");
	}

}
