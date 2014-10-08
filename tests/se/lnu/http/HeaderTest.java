package se.lnu.http;

import static org.junit.Assert.*;

import org.junit.Test;

import se.lnu.http.exceptions.MalformedRequestException;

public class HeaderTest {

	@Test(expected=MalformedRequestException.class)
	public void testFromString() throws MalformedRequestException {
		Header.fromString("");
	}
	
	@Test(expected=MalformedRequestException.class)
	public void testFromString2() throws MalformedRequestException {
		Header.fromString("fouhqofho");
	}
	
	@Test
	public void testFromString3() throws MalformedRequestException {
		Header sut = Header.fromString("Unknown: somevalue");
		
		assertEquals(Header.HTTPHeader.UnknownHeader, sut.getType());
		assertEquals("somevalue", sut.getValue());
	}
	
	@Test
	public void testFromString4() throws MalformedRequestException {
		Header sut = Header.fromString("Connection: close");
		
		assertEquals(Header.HTTPHeader.Connection, sut.getType());
		assertEquals("close", sut.getValue());
	}

}
