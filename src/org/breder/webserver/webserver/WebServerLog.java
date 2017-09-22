package org.breder.webserver.webserver;

import org.breder.webserver.util.Log;

public class WebServerLog extends Log {

  private static final Log log = new WebServerLog();

  public static void info(WebServerCode code, String message, Object... args) {
    log.info("HttpServer", code.code, message, args);
  }

  public static void warning(WebServerCode code, String message, Object... args) {
    log.warning("HttpServer", code.code, message, args);
  }

  public static void severe(WebServerCode code, String message, Object... args) {
    log.severe("HttpServer", code.code, message, args);
  }

  public static void error(WebServerCode code, String message, Object... args) {
    log.error("HttpServer", code.code, message, args);
  }

  public static void error(WebServerCode code, Throwable e) {
    log.error("HttpServer", code.code, e);
  }

  public static void info(String message, Object... args) {
    log.info("HttpServer", 0, message, args);
  }

  public static void warning(String message, Object... args) {
    log.warning("HttpServer", 0, message, args);
  }

  public static void severe(String message, Object... args) {
    log.severe("HttpServer", 0, message, args);
  }

  public static void error(String message, Object... args) {
    log.error("HttpServer", 0, message, args);
  }

  public static void error(Throwable e) {
    e.printStackTrace();
    log.error("HttpServer", 0, e);
  }

  protected int getStackMethodIndex() {
    return 4;
  }

}
