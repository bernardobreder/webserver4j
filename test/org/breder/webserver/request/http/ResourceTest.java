package org.breder.webserver.request.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

import org.breder.webserver.webserver.request.http.Resource;
import org.junit.Assert;
import org.junit.Test;

public class ResourceTest {

	@Test
	public void test() throws IOException {
		Resource r = new Resource("/index.html");
		byte[] bytes = "<html></html>".getBytes();
		Assert.assertTrue(r.exists());
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		DeflaterOutputStream zipOutput = new DeflaterOutputStream(output);
		zipOutput.write(bytes);
		zipOutput.close();
		Assert.assertArrayEquals(output.toByteArray(), r.getGZipBytes());
	}

	@Test
	public void notFound() throws IOException {
		Resource r = new Resource("/index");
		Assert.assertFalse(r.exists());
		Assert.assertNull(r.getGZipBytes());
	}

	@Test(expected = IllegalArgumentException.class)
	public void wrong() throws IOException {
		new Resource("/index/..");
	}

}
