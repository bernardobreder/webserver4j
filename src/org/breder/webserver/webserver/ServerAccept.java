package org.breder.webserver.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerAccept implements Runnable {

  private final ServerSocket server;

  private final SocketProcessor processor;

  private boolean quit;

  private List<SocketThread> threads = new ArrayList<SocketThread>();

  public ServerAccept(int port, SocketProcessor processor) throws IOException {
    this.processor = processor;
    this.server = new ServerSocket(port);
  }

  @Override
  public void run() {
    for (; !quit;) {
      try {
        Socket socket = this.server.accept();
        this.send(socket);
      }
      catch (Throwable e) {
      }
    }
  }

  public void send(Socket socket) {
    for (SocketThread thread : this.threads) {
      if (thread.isFree()) {
        thread.set(processor, socket);
        return;
      }
    }
    SocketThread thread = new SocketThread(this.processor, socket);
    thread.start();
    this.threads.add(thread);
  }

  public void start() {
    new Thread(this, "Accept " + this.server.getLocalPort()).start();
  }

  public void close() {
    quit = true;
  }

  public static boolean isDebug() {
    return "true".equals(System.getProperty("debug"));
  }

}
