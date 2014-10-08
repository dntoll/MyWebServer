package se.lnu.http.response;

import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import se.lnu.http.ClientSocket;
import se.lnu.http.IServerWatcher;

public class HTMLFileResponseTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	//http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
	@Test
	public void testWriteResponse() throws IOException {
		URL url = this.getClass().getResource("../resources/inner/index.html");
		
		File file = new File(url.getFile());
		
		Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		
		ClientSocket clientSocket = mock(ClientSocket.class);
		IServerWatcher watcher = mock(IServerWatcher.class);
		HTTP200OKFileResponse response = new HTTP200OKFileResponse(file, watcher, 1);
		
		
		response.writeResponse(clientSocket, false);
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		verify(clientSocket).writeHeader(argument.capture());
		
		//ArgumentCaptor<byte[]> bodyArgument = ArgumentCaptor.forClass(byte[].class);
		verify(clientSocket).writeFile(file);
		/*String writtenToSocket = argument.getValue();
		
		assertTrue( writtenToSocket.contains("HTTP/1.1 200 OK\r\n"));
		byte[] actualBytes = bodyArgument.getValue();
		for(int i= 0; i< bytes.length; i++) {
			assertEquals(bytes[i] , actualBytes[i]);
		}
		
		verify(watcher).clientGotFile(file, 1);*/
		
		
	}

}
