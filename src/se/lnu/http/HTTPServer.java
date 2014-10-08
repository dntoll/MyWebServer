package se.lnu.http;

import java.io.IOException;
import java.net.ServerSocket;

import se.lnu.http.exceptions.NotStartedException;


public class HTTPServer {

	
	private IServerWatcher watcher;
	
	private Port port;
	private AcceptThread acceptThread;

	private SharedFolder sharedDirectory;

	public HTTPServer(Port port, SharedFolder sharedDirectory, IServerWatcher watcher) {
		this.watcher = watcher;
		watcher.serverConstructed();
		this.port = port;
		this.sharedDirectory = sharedDirectory;
	}

	public void start() throws IOException {
		ServerSocket socket = new ServerSocket(port.getPort());
		ClientFactory factory = new ClientFactory(this.sharedDirectory, watcher);
		acceptThread = new AcceptThread(socket, watcher, factory);
		
		acceptThread.start();
		
		watcher.serverStarted();
	}

	public void stop() throws NotStartedException, InterruptedException, IOException {
		if (acceptThread == null)
			throw new NotStartedException();
		
		acceptThread.stopme();
		
		while(acceptThread.isAlive()) {
			Thread.sleep(100);
		}
		
		
		watcher.serverStopped();
		
		acceptThread = null;
	}

}
