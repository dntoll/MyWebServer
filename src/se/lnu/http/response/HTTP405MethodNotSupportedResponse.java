package se.lnu.http.response;

import java.io.IOException;

import se.lnu.http.ClientSocket;
import se.lnu.http.HTTPRequest.Method;

public class HTTP405MethodNotSupportedResponse extends HTTPResponse {

	private Method method;

	public HTTP405MethodNotSupportedResponse(Method method) {
		this.method = method;
	}

	@Override
	public void writeResponse(ClientSocket clientSocket, boolean doContinue) throws IOException {
		String content = "<html><body><h1>405 Method " +method.toString()+ " not supported</h1></body></html>";
		
		
		String response = ("HTTP/1.1 405 Method not supported\r\n");
		response += writeContentLengthAndEndHeader(content.getBytes().length, ContentType.texthtml, doContinue);
		clientSocket.writeHeader(response);
		clientSocket.writeBody(content.getBytes());
			
		 
	}

}
