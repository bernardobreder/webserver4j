package org.breder.webserver.webserver.net;

import java.io.IOException;

public class ConnectionFactory {

  private static boolean debug = false;

  public static void setDebug(boolean flag) {
    debug = flag;
  }

  public static ConnectionServer createServer(int port) throws IOException {
    if (debug) {
      return new FakeConnectionServer(port);
    }
    else {
      return new SocketConnectionServer(port);
    }
  }

}
