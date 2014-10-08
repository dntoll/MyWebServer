package se.lnu.http.view;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import se.lnu.http.Port;
import se.lnu.http.exceptions.InvalidPortException;
import se.lnu.http.exceptions.NotADirectoryException;
import se.lnu.http.exceptions.WrongNumberOfArgumentsException;
import se.lnu.http.view.ConsoleView;


public class ConsoleViewTest {
	String[] okInput = new String[2];
	
	@Before
	public void setUp() throws Exception {
		okInput[0] = "80";
		okInput[1] = "/";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=WrongNumberOfArgumentsException.class)
	public void testNoArguments() throws Exception {
		String[] input = new String[0];
		ConsoleView view = new ConsoleView(input, System.out);
		
		view.getPort();
	}
	
	@Test(expected=InvalidPortException.class)
	public void testCrapArgument1() throws Exception {
		String[] input = new String[1];
		input[0] = "rfsdf";
		ConsoleView view = new ConsoleView(input, System.out);
		
		view.getPort();
	}
	
	@Test(expected=NotADirectoryException.class)
	public void testNotADirectory1() throws Exception {
		
		URL url = this.getClass().getResource("../resources/inner/index.html");
		File folder = new File(url.getFile());
		
		String[] input = new String[2];
		input[0] = "80";
		input[1] = folder.getAbsolutePath();
		ConsoleView view = new ConsoleView(input, System.out);
		
		view.getDirectory();
	}
	
	@Test(expected=NotADirectoryException.class)
	public void testCrapArgument2() throws Exception {
		String[] input = new String[2];
		input[0] = "80";
		input[1] = "rfsdf";
		ConsoleView view = new ConsoleView(input, System.out);
		
		view.getDirectory();
	}
	
	@Test
	public void testOkPort() throws Exception {
		ConsoleView view = new ConsoleView(okInput, System.out);
		Port actual = view.getPort();
		assertSame(80, actual.getPort());
	}
	
	@Test
	public void testPortTaken() throws Exception {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		PrintStream old = System.out;
		System.setOut(new PrintStream(outContent));
		ConsoleView view = new ConsoleView(okInput, System.out);
		view.showPortTaken();
		
		System.setOut(old);
		
		assertEquals(ConsoleView.PORT_IS_TAKEN+ "\n", outContent.toString());
	}
	
	@Test
	public void testOkDirectory() throws Exception {
		ConsoleView view = new ConsoleView(okInput, System.out);
		File actual = view.getDirectory();
		
		String os = System.getProperty("os.name");
		
		if (os.contains("Windows")) {
			assertEquals("\\", actual.getPath()); // on windows this becomes \\ under linux it is '/'
		} else {
			assertEquals("/", actual.getPath()); // on windows this becomes \\ under linux it is '/'
		}
	}
	
	@Test
	public void testShowhelp() throws Exception  {
		//http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		PrintStream old = System.out;
		
		ConsoleView view = new ConsoleView(okInput, System.out);
		view.showhelp();
		assertEquals(ConsoleView.HELP_TEXT, outContent.toString());
		System.setOut(old);
	}
	
	
	
	@Test
	public void testDoStop() throws Exception  {
		//http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
		
		String data = "stop";
		ByteArrayInputStream inContent = new ByteArrayInputStream(data.getBytes());
		InputStream old = System.in;
		System.setIn(inContent);
		ConsoleView view = new ConsoleView(okInput, System.out);
		boolean actual = view.doStop();
		
		assertEquals(true, actual);
		System.setIn(old);
	}
	
	@Test
	public void testDoNotStop() throws Exception  {
		//http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
		
		String data = "foo";
		ByteArrayInputStream inContent = new ByteArrayInputStream(data.getBytes());
		InputStream old = System.in;
		System.setIn(inContent);
		ConsoleView view = new ConsoleView(okInput, System.out);
		boolean actual = view.doStop();
		
		assertEquals(false, actual);
		System.setIn(old);
	}

}

