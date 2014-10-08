package se.lnu.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
	
	private final Socket sock;
	private byte[] byteArray;

	public ClientSocket(Socket sock) {
		this.sock = sock;
		byteArray = new byte[8*1024];
		
	}

	public String getRequest(int timeOutMilliseconds) throws IOException { 
		
		sock.setSoTimeout(timeOutMilliseconds);
		BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		HTTPReader httpReader = new HTTPReader(in);
		String requestString = httpReader.readAll();
		return requestString;
	}

	public void writeHeader(String response) throws IOException {
		PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
		out.write(response.toString());
		out.flush();
	}

	public void close() throws IOException {
		sock.close();
		
	}

	public void writeBody(byte[] bytes) throws IOException {
		sock.getOutputStream().write(bytes);
	}

	public void writeFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream( file );
		OutputStream out = sock.getOutputStream();
		int bytesRead = 0;
		
		while ((bytesRead = fis.read(byteArray)) != -1) {
			out.write(byteArray, 0, bytesRead);
		}
		
		fis.close();
			
		
	}

}
