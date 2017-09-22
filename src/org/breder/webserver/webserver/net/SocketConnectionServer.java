package org.breder.webserver.webserver.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.breder.webserver.webserver.WebServerCode;
import org.breder.webserver.webserver.WebServerLog;

public class SocketConnectionServer implements ConnectionServer {

  private final ServerSocket server;

  public SocketConnectionServer(int port) throws IOException {
    WebServerLog.info(WebServerCode.SERVER_CREATE,
      "Initing the Vitrinii ServerAccept");
    this.server = new ServerSocket(port);
  }

  @Override
  public Connection accept() throws IOException {
    Socket socket = this.server.accept();
    WebServerLog.info(WebServerCode.SOCKET_CREATE, "Socket created with %s",
      socket.getRemoteSocketAddress());
    socket.setSoTimeout(30 * 1000);
    return new SocketConnection(socket);
  }

  @Override
  public void close() throws IOException {
    WebServerLog.info(WebServerCode.SERVER_CLOSE, "ServerSocket closing");
    this.server.close();
  }

  @Override
  public boolean isClosed() {
    return this.server.isClosed();
  }

  @Override
  public String getServerHost() {
    return this.server.getLocalSocketAddress().toString();
  }

  @Override
  public int getServerPort() {
    return this.server.getLocalPort();
  }

}
