package org.breder.webserver.webserver;

import java.net.Socket;

public interface SocketProcessor {

  public void execute(Socket socket) throws Exception;

}
