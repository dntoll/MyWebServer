package se.lnu.http;

import java.io.File;

public class ServerFactory {

	public HTTPServer create(Port port, File sharedDirectory, IServerWatcher view) {
		return new HTTPServer(port, new SharedFolder(sharedDirectory), view);
	}

}
