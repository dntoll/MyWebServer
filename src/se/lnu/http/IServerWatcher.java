package se.lnu.http;

import java.io.File;

public interface IServerWatcher {

	void serverConstructed();

	void serverStarted();

	void serverStopped();

	void closedAccept();

	void startedClient(int threadId);

	void waitForClient();

	void clientGotFile(File file, int threadId);

	void connectionBroken(int threadId);

}
