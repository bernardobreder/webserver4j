import java.net.Socket;

public class SimpleSocketTest {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 8080);
		socket.setReuseAddress(true);
		Thread.sleep(5000);
		socket.close();
	}

}
