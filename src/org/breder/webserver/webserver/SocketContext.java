package org.breder.webserver.webserver;

import java.net.Socket;

public class SocketContext {

  private final SocketProcessor processor;

  private final Socket socket;

  public SocketContext(Socket socket, SocketProcessor processor) {
    super();
    this.socket = socket;
    this.processor = processor;
  }

  /**
   * @return the processor
   */
  public SocketProcessor getProcessor() {
    return processor;
  }

  /**
   * @return the socket
   */
  public Socket getSocket() {
    return socket;
  }

}
