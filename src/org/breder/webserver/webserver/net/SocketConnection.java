package org.breder.webserver.webserver.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.breder.webserver.webserver.WebServerCode;
import org.breder.webserver.webserver.WebServerLog;

public class SocketConnection implements Connection {

  private final Socket socket;

  public SocketConnection(Socket socket) {
    super();
    this.socket = socket;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OutputStream getOutputStream() throws IOException {
    return this.socket.getOutputStream();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InputStream getInputStream() throws IOException {
    return this.socket.getInputStream();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {
    WebServerLog.info(WebServerCode.SOCKET_CLOSING, "Socket closing with %s",
      socket.getRemoteSocketAddress());
    this.socket.close();
    WebServerLog.info(WebServerCode.SOCKET_CLOSED, "Socket closed with %s",
      socket.getRemoteSocketAddress());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isClosed() {
    return this.socket.isClosed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRemoteAddress() {
    return socket.getRemoteSocketAddress().toString();
  }

}
