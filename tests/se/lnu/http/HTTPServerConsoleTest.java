package se.lnu.http;

import static org.mockito.Mockito.*;

import java.io.File;
import java.net.BindException;

import org.junit.Before;
import org.junit.Test;

import se.lnu.http.exceptions.InvalidPortException;
import se.lnu.http.view.ConsoleView;


public class HTTPServerConsoleTest {

	private HTTPServerConsole console;
	private ConsoleView mockedView;
	private ServerFactory mockedServerFactory;
	private HTTPServer mockedServer;
	Port port;
	File directory;

	@Before
	public void setUp() throws Exception {
		mockedView = mock(ConsoleView.class);
		mockedServerFactory = mock(ServerFactory.class);
		mockedServer = mock(HTTPServer.class);
		
		console = new HTTPServerConsole(mockedView, mockedServerFactory);
		
		port = new Port(80);
		directory = new File("/");
		
		when(mockedServerFactory.create(port, directory, mockedView)).thenReturn(mockedServer);
		
	}

	@Test
	public void testrunConsoleNoPort() throws Exception {
		when(mockedView.getPort()).thenThrow(new InvalidPortException(""));
		console.runConsole();
		verify(mockedView).showhelp();
		verify(mockedServer, never()).start();
		verify(mockedServer, never()).stop();
	}
	
	@Test
	public void testrunConsolePort80() throws Exception {
		
		when(mockedView.getPort()).thenReturn(port);
		when(mockedView.getDirectory()).thenReturn(directory);
		when(mockedView.doStop()).thenReturn(true);
		console.runConsole();
		verify(mockedView, never()).showhelp();
		verify(mockedServer).start();
		verify(mockedServer).stop();
		
		
		
	}
	
	@Test
	public void runOnTakenPort() throws Exception {
		when(mockedView.getPort()).thenReturn(port);
		when(mockedView.getDirectory()).thenReturn(directory);
		
		doThrow(new BindException()).when(mockedServer).start();
		
		console.runConsole();
		verify(mockedView, never()).showhelp();
		verify(mockedServer, never()).stop();
		verify(mockedView).showPortTaken();
		
		
	}
	
}
