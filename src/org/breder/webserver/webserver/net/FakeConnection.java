package org.breder.webserver.webserver.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketAddress;

public class FakeConnection implements Connection {

  private ByteArrayOutputStream output;

  private ByteArrayInputStream input;

  private ByteArrayOutputStream browserOutput;

  private ByteArrayInputStream browserInput;

  private boolean close;

  @Override
  public synchronized OutputStream getOutputStream() throws IOException {
    if (this.close) {
      throw new IllegalStateException();
    }
    if (this.output == null) {
      this.output = new ByteArrayOutputStream();
      this.input = null;
    }
    return this.output;
  }

  @Override
  public synchronized InputStream getInputStream() throws IOException {
    if (this.input == null) {
      if (this.browserOutput == null) {
        this.input = new ByteArrayInputStream(new byte[0]);
      }
      else {
        this.input = new ByteArrayInputStream(this.browserOutput.toByteArray());
        this.browserOutput = null;
      }
    }
    return this.input;
  }

  public synchronized OutputStream getBrowserOutputStream() throws IOException {
    if (this.close) {
      throw new IllegalStateException();
    }
    if (this.browserOutput == null) {
      this.browserOutput = new ByteArrayOutputStream();
      this.browserInput = null;
    }
    return this.browserOutput;
  }

  public synchronized InputStream getBrowserInputStream() throws IOException {
    if (this.browserInput == null) {
      this.browserInput = new ByteArrayInputStream(this.output.toByteArray());
      this.browserOutput = null;
    }
    return this.browserInput;
  }

  @Override
  public void close() throws IOException {
    this.close = true;
  }

  @Override
  public boolean isClosed() {
    return this.close;
  }

  @Override
  public String getRemoteAddress() {
    return "localhost";
  }

}
