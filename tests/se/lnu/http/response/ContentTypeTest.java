package se.lnu.http.response;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContentTypeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetFromFileEnding() {
		assertEquals(ContentType.texthtml, ContentType.getFromFileEnding("html"));
		assertEquals(ContentType.texthtml, ContentType.getFromFileEnding("htm"));
		assertEquals(ContentType.imagepng, ContentType.getFromFileEnding("png"));
		assertEquals(ContentType.applicationunknown, ContentType.getFromFileEnding("gdg"));
		assertEquals(ContentType.texthtml.toString(), "text/html");
	}

}
