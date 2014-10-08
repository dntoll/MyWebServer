package se.lnu.http.response;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import se.lnu.http.ClientSocket;
import se.lnu.http.HTTPRequest.Method;


public class ErrorResponses {
	@Test
	public void test400() throws IOException {
		
		
		HTTPResponse response[] = new HTTPResponse[4];
		response[0]= new HTTP400BadRequest();
		response[1]= new HTTP403Forbidden();
		response[2]= new HTTP404FileNotFoundResponse("");
		response[3]= new HTTP405MethodNotSupportedResponse(Method.CONNECT);
		String expected[] = new String[4];
		expected[0] = "HTTP/1.1 400";
		expected[1] = "HTTP/1.1 403";
		expected[2] = "HTTP/1.1 404";
		expected[3] = "HTTP/1.1 405";
		
		for (int i = 0; i < response.length; i++) {
			
			ClientSocket clientSocket = mock(ClientSocket.class);
			response[i].writeResponse(clientSocket, false);
			ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
			verify(clientSocket).writeHeader(argument.capture());
			
			ArgumentCaptor<byte[]> bodyArgument = ArgumentCaptor.forClass(byte[].class);
			verify(clientSocket).writeBody(bodyArgument.capture());
			String writtenToSocket = argument.getValue();
			
			assertTrue( writtenToSocket.contains(expected[i]));
		}
		
	}
}
