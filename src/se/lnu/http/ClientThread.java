package se.lnu.http;

import java.io.IOException;
import se.lnu.http.exceptions.MalformedRequestException;
import se.lnu.http.response.HTTPResponse;


public class ClientThread extends Thread{
	
	
	private ClientSocket clientSocket;
	private ResponseFactory factory;
	private IServerWatcher watcher;
	private int identifier;
	public static int timeOutMilliseconds = 8000;

	public ClientThread(ClientSocket clientSocket,
						ResponseFactory factory, 
						IServerWatcher watcher, int identifier) {
		this.clientSocket = clientSocket;
		
		this.factory = factory;
		this.watcher = watcher;
		this.identifier = identifier;
	}
	
	public void run() {
		 
		watcher.startedClient(identifier);
		try {
			while(singleRequest());
			
		} catch (IOException e) {
			//connection broken
			
		}
		
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		watcher.connectionBroken(identifier);
		
	}

	private boolean singleRequest() throws IOException {
		boolean doContinue = true;
		String requestString = clientSocket.getRequest(timeOutMilliseconds);
		HTTPResponse response;
		try {
			HTTPRequest request = HTTPRequestParser.parseRequest(requestString);
			
			
			if (request.doCloseAfterResponse()) {
				doContinue = false;
			}
			
			response = factory.getResponse(request);
		} catch (MalformedRequestException e) {
			response = factory.getBadResponse();
			doContinue = false;
		}
	
		response.writeResponse(clientSocket, doContinue);
		
		return doContinue;
	}
}
