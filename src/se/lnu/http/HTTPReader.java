package se.lnu.http;

import java.io.BufferedReader;
import java.io.IOException;

public class HTTPReader {
	private int contentlength = 0;
	
	BufferedReader reader;
	
	public HTTPReader(BufferedReader reader) {
		this.reader = reader;
	}

	private String readBody() throws IOException {
		StringBuilder data = new StringBuilder();
		for (int i = 0; i < contentlength; i++) {
			data.append((char)reader.read());
		}
		
		return data.toString();
	}

	public String readAll() throws IOException {
		StringBuilder data = new StringBuilder();
				
		while (true) {
			String lineRead = reader.readLine();
			
			if (lineRead == null) { //eof http://docs.oracle.com/javase/1.4.2/docs/api/java/io/BufferedReader.html
				throw new IOException("eof");
			}
			
			data.append(lineRead);
			data.append("\r\n");
			if (lineRead == null || lineRead.equals("\r\n") || lineRead.equals("")) {
				break;
			}
			if (lineRead.startsWith("Content-Length:")) {
				String number = lineRead.substring(16);
				contentlength  = Integer.parseInt(number); 
			}
			
		}
		
		return data.toString() + readBody();
	}
}
