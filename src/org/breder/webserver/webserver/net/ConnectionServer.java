package org.breder.webserver.webserver.net;

import java.io.IOException;

public interface ConnectionServer {

  public Connection accept() throws IOException;

  public void close() throws IOException;

  public boolean isClosed();

  public String getServerHost();

  public int getServerPort();

}
