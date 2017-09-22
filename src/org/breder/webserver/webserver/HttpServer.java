package org.breder.webserver.webserver;

import java.io.IOException;

import org.breder.webserver.webserver.project.WebProject;
import org.breder.webserver.webserver.request.HttpProcessor;

/**
 * Classe responsável por inicializar o servidor e enviar todas as conexões para
 * a classe SocketThreadPool. <br>
 * <br>
 * A classe armazena todas as configurações dos projetos web.<br>
 * <br>
 * O servidor irá ser carregado em background para não atrapalhar o andamento da
 * thread corrente.<br>
 * 
 * @author bernardobreder
 * 
 */
public class HttpServer extends ServerAccept {

  /**
   * Construtor padrão
   * 
   * @param port
   * @param project
   * @throws IOException
   */
  public HttpServer(int port, WebProject project) throws IOException {
    super(port, new HttpProcessor(project));
  }

}
