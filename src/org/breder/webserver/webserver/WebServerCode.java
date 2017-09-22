package org.breder.webserver.webserver;

public enum WebServerCode {

  SERVER_CREATE(1),
  SERVER_ACCEPTING(2),
  SERVER_ACCEPTED(3),
  SERVER_CLOSE(4),
  SERVER_START(5),
  SERVER_FINISH(6),
  THREAD_POOL_FULL(7),
  SOCKET_CREATE(8),
  SOCKET_CLOSING(9),
  SOCKET_CLOSED(10),
  SERVER_THREAD_RECEIVE(11),
  SERVER_THREAD_START(12),
  SERVER_THREAD_FINISH(13),
  SERVER_THREAD_FULL(14),
  HTTP_PROCESSOR_REQUESTING(15),
  HTTP_PROCESSOR_REQUESTED(16);

  public final int code;

  private WebServerCode(int code) {
    this.code = code;
  }

}
