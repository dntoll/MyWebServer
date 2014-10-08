package se.lnu.http;

import java.net.Socket;

public class ClientFactory {
	
	private SharedFolder folder;
	private IServerWatcher watcher;
	private int numberOfClients;

	ClientFactory(SharedFolder folder, IServerWatcher watcher) {
		this.folder = folder;
		this.watcher = watcher;
		numberOfClients = 0;
	}

	public ClientThread createClient(Socket clientSocket ) {
		numberOfClients++;
		return new ClientThread(new ClientSocket(clientSocket), new ResponseFactory(folder, watcher, numberOfClients), watcher, numberOfClients);
	}

}
