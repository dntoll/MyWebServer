package se.lnu.http.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import se.lnu.http.HTTPReader;

public class HTTPGetProtocoll {
	

	public String doGet(String hostName, String file,
			BufferedWriter outWriter, BufferedReader reader, boolean doClose) throws IOException {
		
		if (doClose)
			outWriter.write("GET " + file + " HTTP/1.1\r\nHost: " + hostName + "\r\nConnection: close\r\n\r\n");
		else 
			outWriter.write("GET " + file + " HTTP/1.1\r\nHost: " + hostName + "\r\n\r\n");
		outWriter.flush();
		
		HTTPReader httpReader = new HTTPReader(reader);
		String data = httpReader.readAll();
		
		return data;
	}

	
}