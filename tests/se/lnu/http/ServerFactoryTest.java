package se.lnu.http;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;

import org.junit.Test;

import se.lnu.http.exceptions.InvalidPortException;



public class ServerFactoryTest {
	@Test
	public void testCreate() throws InvalidPortException {
		ServerFactory sut = new ServerFactory();
		IServerWatcher mocks = mock(IServerWatcher.class);
		
		
		HTTPServer actual = sut.create(new Port(80), new File("/"), mocks);
		
		assertNotNull(actual);
	}
}
