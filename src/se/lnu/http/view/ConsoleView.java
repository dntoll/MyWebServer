package se.lnu.http.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import se.lnu.http.IServerWatcher;
import se.lnu.http.Port;
import se.lnu.http.exceptions.InvalidPortException;
import se.lnu.http.exceptions.NotADirectoryException;
import se.lnu.http.exceptions.WrongNumberOfArgumentsException;


public class ConsoleView implements IServerWatcher {
	static final String HELP_TEXT = "Enter a valid port 1-65535 and a optional URL";
	static final String SERVER_CONSTRUCTED = "HTTP Server object constructed";
	static final String SERVER_STARTED = "HTTP Server started";
	static final String SERVER_STOPPED = "HTTP Server stopped";
	static final String SERVER_ACCEPT_THREAD_STOPPED = "HTTP Server Accept thread stopped";
	static final String STARTED_CLIENT = "ClientThread started nr: ";
	static final String WAIT_FOR_ACCEPT = "Accept";
	static final String PORT_IS_TAKEN = "Port is taken";
	static final String CLIENT_GOT_FILE = "ClientThread ";
	static final String CONNECTION_WAS_BROKEN = "ClientThread stopped nr: ";
	
	
	private String[] args;
	private PrintStream out;
	
	
	
	public ConsoleView(String[] args, PrintStream out) throws WrongNumberOfArgumentsException {
		if (args.length > 2 || args.length < 1) {
			throw new WrongNumberOfArgumentsException();
		}
		
		this.args = args;
		this.out = out;
	}

	public Port getPort() throws InvalidPortException {
		int port;
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			throw new InvalidPortException("not an integer argument");
		}
		Port p = new Port(port);
		
		return p;
	}

	
	public void showhelp() {
		out.print(HELP_TEXT);
		
	}
	
	

	public File getDirectory() throws NotADirectoryException {
		File directory;
		try {
			directory = new File(args[1]);//may throw nullpointerexception
			
			
			if (directory.exists() == false) {
				throw new NotADirectoryException("directory does not exists");
			}
			if (directory.isDirectory() == false) {
				throw new NotADirectoryException("url is not a directory");
			}
			
		} catch (Exception e) {
			throw new NotADirectoryException(e);
		}
		
		
		return directory;
	}

	public boolean doStop() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String line = br.readLine();
		if (line != null && line.equals("stop")) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public void serverConstructed() {
		out.println(SERVER_CONSTRUCTED);
		
	}

	@Override
	public void serverStarted() {
		out.println(SERVER_STARTED);
		
	}

	@Override
	public void serverStopped() {
		out.println(SERVER_STOPPED);
		
	}

	@Override
	public void closedAccept() {
		out.println(SERVER_ACCEPT_THREAD_STOPPED);
		
	}

	@Override
	public void startedClient(int threadId) {
		out.println(STARTED_CLIENT + threadId);
		
	}

	@Override
	public void waitForClient() {
		out.println(WAIT_FOR_ACCEPT);
		
	}

	public void showPortTaken() {
		out.println(PORT_IS_TAKEN);
	}

	@Override
	public synchronized  void clientGotFile(File file, int threadId) {
		out.println(CLIENT_GOT_FILE + threadId +  " served file : " + file.getName());
		
	}

	@Override
	public void connectionBroken(int threadId) {
		out.println(CONNECTION_WAS_BROKEN + threadId);
		
	}

	

	
}
