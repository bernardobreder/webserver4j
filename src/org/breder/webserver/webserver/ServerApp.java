package org.breder.webserver.webserver;

import java.util.HashMap;
import java.util.Map;

public class ServerApp {

  private Map<Integer, SocketProcessor> map =
    new HashMap<Integer, SocketProcessor>();

  public void add(Integer port, SocketProcessor processor) {
    this.map.put(port, processor);
  }

  public SocketProcessor get(Integer port) {
    return map.get(port);
  }

}
