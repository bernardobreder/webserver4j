package org.breder.webserver.webserver.net;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class FakeConnectionServer implements ConnectionServer {

  private static final Map<Integer, FakeConnectionServer> servers =
    new HashMap<Integer, FakeConnectionServer>();

  private Queue<Connection> connections = new ArrayDeque<Connection>();

  private boolean close;

  public FakeConnectionServer(int port) {
    servers.put(port, this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Connection accept() throws IOException {
    if (this.close) {
      throw new IllegalStateException();
    }
    for (; !this.close;) {
      synchronized (connections) {
        if (connections.size() > 0) {
          return this.connections.poll();
        }
      }
      try {
        Thread.sleep(10);
      }
      catch (InterruptedException e) {
      }
    }
    throw new IOException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {
    this.close = true;
  }

  public synchronized void addConnection(Connection c) {
    this.connections.add(c);
  }

  /**
   * @return the servers
   */
  public static Map<Integer, FakeConnectionServer> getServers() {
    return servers;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isClosed() {
    return this.close;
  }

  @Override
  public String getServerHost() {
    return "localtest";
  }

  @Override
  public int getServerPort() {
    return 8080;
  }

}
