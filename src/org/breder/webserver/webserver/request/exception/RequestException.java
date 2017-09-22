package org.breder.webserver.webserver.request.exception;

/**
 * Erro de requisição. Essa classe será a base de vários tipos de erros
 * 
 * @author bernardobreder
 * 
 */
public abstract class RequestException extends Exception {

  public RequestException() {
    super();
  }

  public RequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public RequestException(String message) {
    super(message);
  }

  public RequestException(Throwable cause) {
    super(cause);
  }

}
