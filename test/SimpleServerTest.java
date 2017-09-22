import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.breder.webserver.webserver.request.RequestHeader;


public class SimpleServerTest {

	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(8080);
		Socket socket = server.accept();
		socket.setReuseAddress(true);
		socket.setOOBInline(true);
		socket.setTcpNoDelay(true);
		// SocketConnection connection = new SocketConnection(socket);
		// RequestHeader header = getHeader(socket);
		// WebSocketProcessor.sendConnectSafari(connection, header);
		while (!socket.isClosed()&&socket.isConnected()&&socket.isBound()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Finish");
	}

	public static RequestHeader getHeader(Socket socket) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		InputStream input = socket.getInputStream();
		int state = 0;
		for (;;) {
			int n = input.read();
			output.write(n);
			if (n == '\r' || n == '\n') {
				state++;
			} else {
				state = 0;
			}
			if (state == 4) {
				return new RequestHeader(output.toByteArray());
			}
		}
	}

}
