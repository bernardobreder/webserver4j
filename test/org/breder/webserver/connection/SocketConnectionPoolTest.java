package org.breder.webserver.connection;

import java.io.IOException;

import org.breder.webserver.webserver.connection.ContextConnection;
import org.breder.webserver.webserver.connection.SocketConnectionPool;
import org.breder.webserver.webserver.net.ConnectionFactory;
import org.breder.webserver.webserver.net.FakeConnection;
import org.breder.webserver.webserver.project.WebProject;
import org.breder.webserver.webserver.project.WebProjects;
import org.breder.webserver.webserver.request.RequestHeader;
import org.breder.webserver.webserver.request.http.HttpSocketProcessor;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class SocketConnectionPoolTest {

	@BeforeClass
	public static void beforeClass() throws IOException {
		ConnectionFactory.setDebug(true);
	}

	@Test
	public void test() throws IOException {
		int size = (int) Math.pow(2, 16);
		SocketConnectionPool pool = new SocketConnectionPool(null, 16);
		try {
			WebProjects project = new WebProjects();
			project.set(new WebProject());
			for (int n = 0; n < size * 2; n++) {
				FakeConnection c = new FakeConnection();
				RequestHeader requestHeader = new RequestHeader("GET", "/index.html", "HTTP/1.1");
				c.getOutputStream().write(requestHeader.toString().getBytes());
				if (n < Math.pow(2, 16)) {
					Assert.assertTrue(pool.add(new ContextConnection(pool, project, c)));
				} else {
					Assert.assertFalse(pool.add(new ContextConnection(pool, project, c)));
				}
			}
		} finally {
			pool.close();
		}
	}

	@Test
	public void testTwo() throws IOException, InterruptedException {
		int size = (int) Math.pow(2, 16);
		SocketConnectionPool pool = new SocketConnectionPool(null, 16);
		try {
			WebProjects project = new WebProjects();
			project.set(new WebProject());
			for (int n = 0; n < size; n++) {
				FakeConnection c = new FakeConnection();
				RequestHeader requestHeader = new RequestHeader("GET", "/index.html", "HTTP/1.1");
				c.getOutputStream().write(requestHeader.toString().getBytes());
				Assert.assertTrue(pool.add(new ContextConnection(pool, project, c)));
			}
			Thread.sleep(HttpSocketProcessor.TIMEOUT);
			for (int n = 0; n < size; n++) {
				FakeConnection c = new FakeConnection();
				RequestHeader requestHeader = new RequestHeader("GET", "/index.html", "HTTP/1.1");
				c.getOutputStream().write(requestHeader.toString().getBytes());
				Assert.assertTrue(pool.add(new ContextConnection(pool, project, c)));
			}
		} finally {
			pool.close();
		}
	}

}
