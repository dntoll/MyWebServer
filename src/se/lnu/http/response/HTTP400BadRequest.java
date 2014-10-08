package se.lnu.http.response;

import java.io.IOException;

import se.lnu.http.ClientSocket;

public class HTTP400BadRequest extends HTTPResponse {

	@Override
	public void writeResponse(ClientSocket clientSocket, boolean doContinue) throws IOException {
		String content = "<html><body><h1>400 Bad request</h1></body></html>";
		String response = ("HTTP/1.1 400 Bad request\r\n");
		response += writeContentLengthAndEndHeader(content.getBytes().length, ContentType.texthtml, doContinue);
		clientSocket.writeHeader(response);
		clientSocket.writeBody(content.getBytes());
	}

}
