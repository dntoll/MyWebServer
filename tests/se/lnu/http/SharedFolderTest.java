package se.lnu.http;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SharedFolderTest {
	private SharedFolder sut;

	@Before
	public void setUp() throws Exception {
		URL url = this.getClass().getResource("resources/inner");
		File folder = new File(url.getFile());
		sut = new SharedFolder(folder);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetRootURL() throws IOException {
		File actual = sut.getURL("/");
		String name = actual.getName();
		assertEquals("index.html", name);
	}
	
	@Test(expected=FileNotFoundException.class)
	public void testGetNonExistantFile() throws IOException {
		sut.getURL("/pindex.html");
	}
	
	@Test(expected=SecurityException.class)
	public void testGetIllegalFile() throws IOException {
		sut.getURL("../secret.html");
		
	}

}
