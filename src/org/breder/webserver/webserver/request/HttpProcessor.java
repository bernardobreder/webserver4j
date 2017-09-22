package org.breder.webserver.webserver.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.breder.webserver.util.DateUtil;
import org.breder.webserver.webserver.ServerAccept;
import org.breder.webserver.webserver.SocketProcessor;
import org.breder.webserver.webserver.WebServerCode;
import org.breder.webserver.webserver.WebServerLog;
import org.breder.webserver.webserver.project.WebProject;
import org.breder.webserver.webserver.request.exception.ParameterRequestException;
import org.breder.webserver.webserver.request.http.HttpRequest;
import org.breder.webserver.webserver.request.http.HttpResponse;
import org.breder.webserver.webserver.request.http.HttpServlet;
import org.breder.webserver.webserver.request.http.Resource;

/**
 * Processador de uma requisição HTTP
 * 
 * 
 * @author bernardobreder
 */
public class HttpProcessor implements SocketProcessor {

  /** Projeto */
  private final WebProject project;

  /**
   * Constructor
   * 
   * @param project
   */
  public HttpProcessor(WebProject project) {
    this.project = project;
  }

  /**
   * {@inheritDoc}
   * 
   * @throws IOException
   */
  @Override
  public void execute(Socket socket) throws Exception {
    // Atribui um timeout
    socket.setSoTimeout(10 * 1024);
    // Conteúdo do cabeçalho
    String headerContent = this.readHeader(socket.getInputStream());
    // Se for nulo
    if (headerContent == null) {
      // Fecha o socket
      socket.close();
      // Finaliza o processador
      return;
    }
    // Constroi o header
    RequestHeader header = new RequestHeader(headerContent);
    // Recupera o servlet
    String servletName = header.getServlet();
    HttpServlet servlet = this.project.getServlet(servletName);
    try {
      // Se tiver servlet
      if (servlet != null) {
        // Executa o servlet
        this.executeServlet(socket, project, header, servlet);
      }
      else {
        // Executa o recurso
        this.executeResource(socket, project, header);
      }
    }
    catch (SocketException e) {
      // Fecha a conexão
      socket.close();
    }
    catch (ParameterRequestException e) {
      // Responde com um erro
      this.writeError(socket.getOutputStream(), 400, "Bad Request",
        getContentType(servletName), e.getMessage());
    }
    catch (Throwable e) {
      // Imprime o erro
      WebServerLog.error(e);
      // Responde com um erro
      this.writeError(socket.getOutputStream(), 500, "Internal Server Error",
        getContentType(servletName), "");
    }
  }

  /**
   * Executa os recursos
   * 
   * @param socket
   * @param header
   * @param project
   * @throws IOException
   */
  public void executeResource(Socket socket, WebProject project,
    RequestHeader header) throws IOException {
    // Recupera o nome do servlet
    String servletName = header.getServlet();
    // Recupera o cache
    HttpResponse response = project.getHttpCache(servletName);
    // Bytes do recurso
    byte[] bytes = null;
    // Verifica se está no cache
    if (response == null) {
      // Protege a execução do servlet
      WebServerLog.info("Processing " + servletName + " resource");
      // Cria um recurso
      Resource res = new Resource(servletName);
      // Se existir
      if (res.exists()) {
        // Recupera os bytes
        bytes = res.getGZipBytes();
        // Cria uma resposta
        response = new HttpResponse(getContentType(servletName), bytes);
      }
      else {
        // Cria uma resposta
        response = new HttpResponse(getContentType(servletName), new byte[0]);
      }
      // Verifica se está no modo debug
      if (!ServerAccept.isDebug()) {
        // Popula o cache
        project.putHttpCache(servletName, response);
      }
    }
    else {
      // Protege a execução do servlet
      WebServerLog.info("Processing " + servletName + " cached resource");
      // Recupera os bytes
      bytes = response.getBytes();
    }
    if (bytes != null) {
      // Responde com os bytes do recurso
      this.writeSuccess(socket.getOutputStream(), true,
        getContentType(servletName), null, bytes);
    }
    else {
      // Responde dizendo que não encontrou
      this.writeError(socket.getOutputStream(), 404, "Not Found",
        getContentType(servletName), "");
    }
  }

  /**
   * Executa o servlet
   * 
   * @param socket
   * @param header
   * @param project
   * @param servlet
   * @throws Exception
   * @throws IOException
   */
  public void executeServlet(Socket socket, WebProject project,
    RequestHeader header, HttpServlet servlet) throws Exception {
    // Recupera o nome do servlet
    String servletName = header.getServlet();
    // Cria uma requisição
    HttpRequest request = new HttpRequest(this, socket, header);
    // Variavel de resposta
    HttpResponse response;
    // Verifica se a requisição é de cache
    if (servlet.isCached(request)) {
      // Recupera o cache
      response = project.getHttpCache(servletName);
      // Se não tiver cache
      if (response == null) {
        // Protege a execução do servlet
        WebServerLog.info(WebServerCode.HTTP_PROCESSOR_REQUESTING,
          "Processing %s servlet", header.getServlet());
        // Cria uma resposta
        response = new HttpResponse(getContentType(servletName));
        // Chama o serviço
        servlet.service(request, response);
        // Fecha o compactador
        response.close();
        // Popula o cache
        project.putHttpCache(servletName, response);
      }
      else {
        // Protege a execução do servlet
        WebServerLog.info(WebServerCode.HTTP_PROCESSOR_REQUESTING,
          "Processing %s cached servlet", header.getServlet());
      }
      // Escreve a saída
      this.writeSuccess(socket.getOutputStream(), true, response
        .getContentType(), null, response.getBytes());
    }
    else {
      // Protege a execução do servlet
      WebServerLog.info(WebServerCode.HTTP_PROCESSOR_REQUESTING,
        "Processing %s servlet", header.getServlet());
      // Popula o cache
      project.removeHttpCache(servletName);
      // Cria uma resposta
      response = new HttpResponse(getContentType(servletName));
      // Chama o serviço
      servlet.service(request, response);
      // Fecha o compactador
      response.close();
      // Verifica se tem session
      Map<String, String> cookies = null;
      if (request.hasSession()) {
        cookies = new HashMap<String, String>();
        cookies.put("Session", request.getSession());
      }
      // Escreve a saída
      this.writeSuccess(socket.getOutputStream(), false, response
        .getContentType(), cookies, response.getBytes());
    }
  }

  /**
   * Responde com sucesso
   * 
   * @param output
   * @param cache
   * @param contentType
   * @param cookies
   * @param bytes
   * @throws IOException
   */
  protected void writeSuccess(OutputStream output, boolean cache,
    String contentType, Map<String, String> cookies, byte[] bytes)
    throws IOException {
    output.write("HTTP/1.0 200 OK\r\n".getBytes());
    if (cache) {
      output.write("Connection: keep-alive\r\n".getBytes());
      output.write("Cache-Control: public, max-age=30\r\n".getBytes());
      output.write(("Date: " + new Date().toString() + "\r\n").getBytes());
      output
        .write(("Expires: " + DateUtil.addDays(new Date(), 1).toString() + "\r\n")
          .getBytes());
    }
    output.write(("Content-Type: " + contentType + "\r\n").getBytes());
    output.write("Content-Encoding: gzip\r\n".getBytes());
    output.write(("Content-Length: " + bytes.length + "\r\n").getBytes());
    if (cookies != null && cookies.size() > 0) {
      for (String key : cookies.keySet()) {
        output.write(("Set-Cookie: " + key + "=" + cookies.get(key) + "\r\n")
          .getBytes());
      }
    }
    output.write("\r\n".getBytes());
    output.write(bytes);
    // WebServerLog.info(WebServerCode.HTTP_PROCESSOR_REQUESTED,
    // "Processed " + header.getServlet() + " with success");
  }

  /**
   * Responde com o erro
   * 
   * @param output
   * @param code
   * @param codeName
   * @param contentType
   * @param message
   * @throws IOException
   */
  protected void writeError(OutputStream output, int code, String codeName,
    String contentType, String message) throws IOException {
    byte[] bytes = message.getBytes("utf-8");
    output.write(("HTTP/1.1 " + code + " " + codeName + "\r\n").getBytes());
    output.write("Connection: Keep-Alive\r\n".getBytes());
    output.write(("Content-Length: " + bytes.length + "\r\n").getBytes());
    output.write(("Content-Type: " + contentType + "\r\n").getBytes());
    output.write('\r');
    output.write('\n');
    output.write(bytes);
    WebServerLog.info(WebServerCode.HTTP_PROCESSOR_REQUESTED,
      "Processed with error (" + code + ") " + codeName + " " + message);
  }

  /**
   * Realiza a leitura do cabeçalho
   * 
   * @param input
   * @return header
   * @throws IOException
   */
  protected String readHeader(InputStream input) throws IOException {
    StringBuilder sb = new StringBuilder();
    int state = 0;
    while (state < 4) {
      int c = input.read();
      if (c == '\r' || c == '\n') {
        state++;
      }
      else {
        state = 0;
      }
      if (sb.length() >= 8 * 1024) {
        return null;
      }
      sb.append((char) c);
    }
    return sb.toString().trim();
  }

  /**
   * Retorna o tipo do recurso baseado no nome do servlet
   * 
   * @param servlet
   * @return tipo do recurso
   */
  public static String getContentType(String servlet) {
    if (servlet.equals("/")) {
      servlet = "index.html";
    }
    if (servlet.endsWith(".jpg") || servlet.endsWith(".jpeg")) {
      return "image/jpeg";
    }
    if (servlet.endsWith(".tiff") || servlet.endsWith(".tif")) {
      return "image/tiff";
    }
    else if (servlet.endsWith(".png")) {
      return "image/png";
    }
    else if (servlet.endsWith(".gif")) {
      return "image/gif";
    }
    else if (servlet.endsWith(".ico")) {
      return "image/ico";
    }
    else if (servlet.endsWith(".zip")) {
      return "application/zip";
    }
    else if (servlet.endsWith(".gz")) {
      return "application/x-gzip";
    }
    else if (servlet.endsWith(".tar")) {
      return "application/x-tar";
    }
    else if (servlet.endsWith(".jar")) {
      return "application/java-archive";
    }
    else if (servlet.endsWith(".js")) {
      return "text/javascript";
    }
    else if (servlet.endsWith(".css")) {
      return "text/css";
    }
    else if (servlet.endsWith(".mf")) {
      return "text/cache-manifest";
    }
    else if (servlet.endsWith(".xml")) {
      return "text/xml";
    }
    else if (servlet.endsWith(".json")) {
      return "text/json";
    }
    else if (servlet.endsWith(".html") || servlet.endsWith(".htm")) {
      return "text/html";
    }
    else if (servlet.endsWith(".pdf")) {
      return "application/pdf";
    }
    else if (servlet.endsWith(".avi")) {
      return "video/x-msvideo";
    }
    else if (servlet.endsWith(".mov")) {
      return "video/quicktime";
    }
    else if (servlet.endsWith(".mpeg") || servlet.endsWith(".mpe")) {
      return "video/mpeg";
    }
    else {
      return "text/plain; charset=UTF-8";
    }
  }

  /**
   * @return the project
   */
  public WebProject getProject() {
    return project;
  }

}
