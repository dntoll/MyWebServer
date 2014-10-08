package se.lnu.http;

import org.junit.Test;

import se.lnu.http.exceptions.InvalidPortException;


public class PortTest {

	

	@Test(expected=InvalidPortException.class)
	public void testPort0() throws InvalidPortException {
		new Port(0);
	}
	@Test(expected=InvalidPortException.class)
	public void testPortTooLarge() throws InvalidPortException {
		new Port(65535 + 1);
	}
	
	@Test
	public void testPortOk() throws InvalidPortException {
		new Port(80);
		//no exception
	}

}
