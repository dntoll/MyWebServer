package se.lnu.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class AcceptThread extends Thread{

	ServerSocket socket;
	private IServerWatcher observer;
	private ClientFactory cfactory;
	
	public AcceptThread(ServerSocket sock, 
						IServerWatcher observer, 
						ClientFactory cfactory) {
		socket = sock;
		this.observer = observer;
		this.cfactory = cfactory;
	}
	
	public void stopme() throws IOException {
		socket.close();
	}
	
	public void run() {
        
        	
        	while (true) {
        		try {
        			acceptClient();
        		} catch (SocketException e) {
        			//when stopme is called we get a socket exception
        			break;
                } catch (IOException e) {
        			e.printStackTrace();
                }
				
				
        	}
        	
        	observer.closedAccept();
        	
		
    }

	private void acceptClient() throws IOException {
		observer.waitForClient();
		Socket clientSocket = socket.accept();
		ClientThread client = cfactory.createClient(clientSocket);
		client.start();
		
	} 
	
}
