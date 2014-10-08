package se.lnu.http;

import java.io.File;
import java.net.BindException;

import se.lnu.http.exceptions.InvalidPortException;
import se.lnu.http.view.ConsoleView;


public class HTTPServerConsole {
	private ConsoleView view;
	private ServerFactory factory;

	public HTTPServerConsole(ConsoleView view, ServerFactory factory ) {
		this.view = view;
		this.factory = factory;
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ConsoleView view = new ConsoleView(args, System.out);
		ServerFactory factory = new ServerFactory();
		HTTPServerConsole server = new HTTPServerConsole(view, factory);
		server.runConsole();
		
		
	}

	void runConsole() throws Exception {
		
		try {
			Port port = view.getPort();
			File sharedDirectory = view.getDirectory();
			HTTPServer server = factory.create(port, sharedDirectory, view);
			
			server.start();
			
			while(true) {
				if (view.doStop()) {
					server.stop();
					break;
				}
			}
			
		} catch (InvalidPortException e) {
			view.showhelp();
		} catch (BindException e) {
			view.showPortTaken();
		}
		
	}

}
