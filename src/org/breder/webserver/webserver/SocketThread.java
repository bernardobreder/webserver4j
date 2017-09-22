package org.breder.webserver.webserver;

import java.io.IOException;
import java.net.Socket;

/**
 * Thread responsável por tratar as requisições de todas as suas conexões.
 * 
 * @author bernardobreder
 * 
 */
public class SocketThread extends Thread {

  /** Processador */
  private SocketProcessor processor;
  /** Socket */
  private Socket socket;

  /**
   * Construtor
   * 
   * @param context
   */
  public SocketThread(SocketProcessor processor, Socket socket) {
    // Armazenando os argumentos
    this.processor = processor;
    this.socket = socket;
    // Alterando o nome da Thread
    this.setName("Socket Thread " + socket.getLocalPort());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    for (; socket != null;) {
      // Logando a criação da Thread
      WebServerLog.info(WebServerCode.SERVER_THREAD_START,
        "Socket Thread (%d) started", Thread.currentThread().getId());
      try {
        try {
          // Delega a execução para a interface do protocolo
          this.processor.execute(this.socket);
        }
        catch (IOException e) {
        }
        catch (Exception e) {
          // Loga o erro
          WebServerLog.error(e);
        }
      }
      finally {
        // Fechando o socket
        try {
          this.socket.close();
        }
        catch (IOException e) {
        }
        // Logando a finalização da Thread
        WebServerLog.info(WebServerCode.SERVER_THREAD_FINISH,
          "Socket Thread (%d) finished", Thread.currentThread().getId());
      }
      this.socket = null;
      synchronized (this) {
        try {
          this.wait(10 * 1000);
        }
        catch (InterruptedException e) {
        }
      }
    }
  }

  /**
   * @return the free
   */
  public boolean isFree() {
    return this.socket == null && this.isAlive();
  }

  /**
   * Troca o ambiente para ser reprocessado
   * 
   * @param processor
   * @param socket
   */
  public void set(SocketProcessor processor, Socket socket) {
    this.processor = processor;
    this.socket = socket;
    synchronized (this) {
      this.notify();
    }
  }

}
