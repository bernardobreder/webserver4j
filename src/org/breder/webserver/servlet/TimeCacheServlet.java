package org.breder.webserver.servlet;

import org.breder.webserver.webserver.ServerAccept;
import org.breder.webserver.webserver.request.http.HttpRequest;
import org.breder.webserver.webserver.request.http.HttpServlet;

/**
 * Servlet com cache baseado no tempo
 * 
 * 
 * @author Tecgraf
 */
public abstract class TimeCacheServlet extends HttpServlet {

  /** Timer */
  private long timer;

  /**
   * Construtor
   */
  public TimeCacheServlet() {
    this.timer = System.currentTimeMillis();
  }

  /**
   * Indica o timeout
   * 
   * @return timeout
   */
  protected int getTimeout() {
    return 60 * 1000;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCached(HttpRequest req) {
    if (ServerAccept.isDebug()) {
      return false;
    }
    if (System.currentTimeMillis() - this.timer > this.getTimeout()) {
      this.timer = System.currentTimeMillis();
      return false;
    }
    else {
      return true;
    }
  }

}
