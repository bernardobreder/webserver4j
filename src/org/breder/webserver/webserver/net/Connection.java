package org.breder.webserver.webserver.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Connection {

  public OutputStream getOutputStream() throws IOException;

  public InputStream getInputStream() throws IOException;

  public void close() throws IOException;

  public boolean isClosed();

  public String getRemoteAddress();

}
