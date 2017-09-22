package org.breder.webserver.webserver.project;

import org.breder.webserver.util.SoftMap;
import org.breder.webserver.webserver.request.http.HttpResponse;
import org.breder.webserver.webserver.request.http.HttpServlet;

/**
 * Projeto Web
 * 
 * @author bernardobreder
 * 
 */
public class WebProject {

  /** Dominio Servlet */
  private final WebProjectDomain<HttpServlet> servlet =
    new WebProjectDomain<HttpServlet>();
  // /** Dominio Message */
  // private final WebProjectDomain<HttpMessage> message = new
  // WebProjectDomain<HttpMessage>();
  /** Cache do projeto */
  private final SoftMap<String, HttpResponse> cache =
    new SoftMap<String, HttpResponse>();

  /**
   * Recupera um servlet
   * 
   * @param servlet
   * @return servlet
   */
  public HttpServlet getServlet(String servlet) {
    while (servlet.endsWith("/")) {
      servlet = servlet.substring(0, servlet.length() - 1);
    }
    return this.servlet.get(servlet);
  }

  /**
   * Adiciona um servlet
   * 
   * @param pattern
   * @param servlet
   * @return this
   */
  public WebProject addServlet(String pattern, HttpServlet servlet) {
    while (pattern.endsWith("/")) {
      pattern = pattern.substring(0, pattern.length() - 1);
    }
    this.servlet.add(pattern, servlet);
    servlet.setServletName(pattern);
    return this;
  }

  // public HttpMessage getMessage(String servlet) {
  // return this.message.get(servlet);
  // }
  //
  // public void addMessage(String name, HttpMessage servlet) {
  // this.message.add(name, servlet);
  // }

  /**
   * @param servlet
   * @return the cache
   */
  public synchronized HttpResponse getHttpCache(String servlet) {
    return cache.get(servlet);
  }

  /**
   * Atribui ao cache
   * 
   * @param servlet
   * @param bytes
   */
  public synchronized void putHttpCache(String servlet, HttpResponse response) {
    cache.put(servlet, response);
  }

  /**
   * Remove o cache de um recurso
   * 
   * @param servlet
   */
  public void removeHttpCache(String servlet) {
    cache.remove(servlet);
  }

}
