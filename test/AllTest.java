import org.breder.webserver.WebServerTest;
import org.breder.webserver.connection.SocketConnectionPoolTest;
import org.breder.webserver.request.http.HttpSocketProcessorTest;
import org.breder.webserver.request.http.ResourceTest;
import org.breder.webserver.webserver.net.ConnectionFactory;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ WebServerTest.class, SocketConnectionPoolTest.class, HttpSocketProcessorTest.class, ResourceTest.class })
public class AllTest {

	@BeforeClass
	public static void beforeClass() {
		ConnectionFactory.setDebug(true);
	}

}
