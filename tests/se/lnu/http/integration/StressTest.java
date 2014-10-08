package se.lnu.http.integration;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.lnu.http.HTTPServer;
import se.lnu.http.IServerWatcher;
import se.lnu.http.Port;
import se.lnu.http.SharedFolder;
import se.lnu.http.client.HTTPGetProtocoll;
import se.lnu.http.exceptions.NotStartedException;
import se.lnu.http.view.ConsoleView;

public class StressTest {

	private static final long RUN_TIME_MILLIS = 1000;
	private IServerWatcher observer;
	private HTTPServer sut;
	private String hostname = "127.0.0.1";
	private int portNumber = 8089;
	private int NUMBEROFTHREADS = 5;
	private ClientThread[] threads = new ClientThread[NUMBEROFTHREADS];

	public class NullOutputStream extends OutputStream {
	  @Override
	  public void write(int b) throws IOException {
	  }
	}
	
	public class NullPrintStream extends PrintStream {

		public NullPrintStream(OutputStream out) {
			super(out);
			// TODO Auto-generated constructor stub
		}
		  
		}
	
	@Before
	public void setUp() throws Exception {
		String args[] = new String[2];
		observer = new ConsoleView(args, new NullPrintStream(new NullOutputStream()));
		URL url = this.getClass().getResource("../resources/inner");
		File folder = new File(url.getFile());
		SharedFolder sharedDirectory = new SharedFolder(folder);
		sut = new HTTPServer(new Port(portNumber ), sharedDirectory, observer);
		sut.start();
		
		
		for (int i =0;i< NUMBEROFTHREADS; i++) {
			threads[i] = new ClientThread();
		}
	}
	
	

	@After
	public void tearDown() throws NotStartedException, InterruptedException, IOException {
		sut.stop();
		
	}
	
	@Test
	public void stressTest() {
		for (int i =0;i< NUMBEROFTHREADS; i++) {
			threads[i].start();
		}
		try {
			Thread.sleep(RUN_TIME_MILLIS);
		} catch (InterruptedException e) {
			
		}
		
		for (int i =0;i< NUMBEROFTHREADS; i++) {
			threads[i].interrupt();
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int fails = 0;
		int oks = 0;
		int exceptions = 0;
		for (int i =0;i< NUMBEROFTHREADS; i++) {
			fails += threads[i].fails;
			oks += threads[i].oks;
			exceptions += threads[i].exceptions;
		}
		System.out.println("fails: " + fails + " oks: " + oks + " exceptions: " + exceptions +" total " + (fails+oks+exceptions));
		assertEquals(0, fails);
		assertEquals(0, exceptions);
		
		
		
	}
	
	class ClientThread extends Thread {
		String expected = "HTTP/1.1 200 OK\r\n";
		private int fails = 0;
		private int oks = 0;
		private int exceptions = 0;
		private Socket socket;
		private BufferedWriter writer;
		private BufferedReader reader;
		private HTTPGetProtocoll client = new HTTPGetProtocoll();
		Random rand = new Random();
		
		public void setupSocket(String hostName, int portNumber) throws IOException {
			socket = new Socket();
			InetSocketAddress endpoint = new InetSocketAddress(hostName, portNumber);
			socket.connect(endpoint, 100);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		}
		
		public void shutdownSocket() throws IOException {
			
			socket.close();
			socket = null;
			
		}

		public boolean tryConnect(String hostName, boolean doClose) throws IOException {
			
			
			String actual = client.doGet(hostName, "/", writer, reader, doClose);
			
			
			return actual.startsWith(expected);
		}

		@Override
		public void run() {
			try {
				while(!Thread.interrupted() ) {
					
					try {
						setupSocket(hostname, portNumber);
					} catch (IOException e1) {
						Thread.sleep(rand.nextInt(1000));
						continue;
					}
				
					int numAttempts = rand.nextInt(10);
					for (int i  =0; i< numAttempts; i++) {
						boolean doClose = i == numAttempts-1;
						try {
							if (tryConnect(hostname, doClose) == false) {
								fails++;
							} else {
								oks++;
							}
							Thread.sleep(rand.nextInt(10));
						} catch (IOException e) {
							System.err.println(e.toString());
							//java.net.ConnectException: Cannot assign requested address
							//https://groups.google.com/forum/#!topic/jvm-languages/E-8QyckBmf4
							exceptions++;
						} catch (InterruptedException e) {
							break;
						}
					}
				
				
					try {
						shutdownSocket();
					} catch (IOException e) {
						
					}
				}
			}  catch (InterruptedException e) {
				//done
			}
		}
	}
}
