package se.lnu.http.response;

import java.io.File;
import java.io.IOException;
import se.lnu.http.ClientSocket;
import se.lnu.http.IServerWatcher;

public class HTTP200OKFileResponse extends HTTPResponse {

	private File file;
	private IServerWatcher watcher;
	private int clientThread;

	public HTTP200OKFileResponse(File file, IServerWatcher watcher, int clientThread) {
		this.file = file;
		this.watcher = watcher;
		this.clientThread = clientThread;
	}

	@Override
	public void writeResponse(ClientSocket clientSocket, boolean doContinue) throws IOException {
		String fileName = file.getName();
		String parts[] = fileName.split("\\.");
		ContentType type = ContentType.getFromFileEnding(parts[parts.length-1]);
		String response = ("HTTP/1.1 200 OK\r\n");
		
		response += writeContentLengthAndEndHeader(file.length(), type, doContinue);
		clientSocket.writeHeader(response);
		clientSocket.writeFile(file);
		
		watcher.clientGotFile(file, clientThread);
	}

}
