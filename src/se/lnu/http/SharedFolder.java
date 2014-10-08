package se.lnu.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SharedFolder {

	private File sharedDirectory;
	

	public SharedFolder(File sharedDirectory) {
		assert(sharedDirectory.isDirectory());
		this.sharedDirectory = sharedDirectory;
	}

	public synchronized File getURL(String relativePath) throws IOException {
		String path = relativePath;
		//default file
		if (relativePath.endsWith("/")) {
			path += "index.html";
		}
		File file = new File(sharedDirectory, path);
		
		String absolute = sharedDirectory.getAbsolutePath();
		String sharedFileCanonicalPath = file.getCanonicalPath();
		String startOfFile = sharedFileCanonicalPath.substring(0, absolute.length());
		if (startOfFile.equals(absolute) == false) {
			throw new SecurityException();	
		}
		if (file.exists()) {
			return file;
		}
		
		throw new FileNotFoundException();
		
	}

}
