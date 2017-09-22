package org.breder.webserver;
import java.io.IOException;

import org.breder.webserver.webserver.HttpServer;
import org.breder.webserver.webserver.net.ConnectionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class WebServerTest {

	private static HttpServer server;

	@BeforeClass
	public static void beforeClass() throws IOException {
		ConnectionFactory.setDebug(true);
		server = new HttpServer(8080).start();
	}

	@AfterClass
	public static void afterClass() throws IOException {
		server.close();
	}

	@Test
	public void twoServer() throws Exception {
//		FakeConnection c = new FakeConnection();
//		c.getOutputStream().write("GET / HTTP1.1\r\n".getBytes());
//		c.getOutputStream().write("Host: localhost\r\n".getBytes());
//		c.getOutputStream().write("\r\n".getBytes());
//		FakeConnectionServer.getServers().get(80).addConnection(c);
//		FileInputStream finput = new FileInputStream("index.html");
//		try {
//			Assert.assertEquals(c.getInputStream(), finput);
//		} finally {
//			finput.close();
//		}
	}

}
