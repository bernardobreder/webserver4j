package org.breder.webserver.request.http;

import org.breder.webserver.webserver.connection.ContextConnection;
import org.breder.webserver.webserver.net.FakeConnection;
import org.breder.webserver.webserver.project.WebProjects;
import org.breder.webserver.webserver.request.http.HttpSocketProcessor;
import org.junit.Test;


public class HttpSocketProcessorTest {

	@Test
	public void test() {
		WebProjects context = new WebProjects();
		FakeConnection socket = new FakeConnection();
		ContextConnection connection = new ContextConnection(null, context, socket);
		HttpSocketProcessor processor = new HttpSocketProcessor(context, connection);
	}

}
