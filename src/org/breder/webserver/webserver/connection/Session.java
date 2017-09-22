package org.breder.webserver.webserver.connection;

public class Session {

  /** Sessões */
  private static long ids = 0;
  /** Código da sessão */
  private long id;

  /**
   * Construtor
   */
  public Session() {
    this.id = createId();
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Criador de código de sessão
   * 
   * @return código de sessão
   */
  private synchronized long createId() {
    return ++ids;
  }

}
