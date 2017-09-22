package org.breder.webserver.webserver.request.http;

import java.util.regex.Pattern;

/**
 * Especificação de um servlet
 * 
 * 
 * @author Bernardo Breder
 */
public abstract class HttpServlet {

  /** Nome do Servlet */
  private String servletName;
  /** Pattern do Servlet Name */
  private Pattern pattern;

  /**
   * Implementa a requisição
   * 
   * @param req
   * @param resp
   * @throws Exception
   */
  public abstract void service(HttpRequest req, HttpResponse resp)
    throws Exception;

  /**
   * Indica se deseja fazer o cache da resposta
   * 
   * @param req
   * @return indica se fará cache
   */
  public boolean isCached(HttpRequest req) {
    return false;
  }

  /**
   * @return the servletName
   */
  public String getServletName() {
    return servletName;
  }

  /**
   * @param servletName the servletName to set
   */
  public void setServletName(String servletName) {
    this.servletName = servletName;
  }

  /**
   * Retorna o pattern do servlet name
   * 
   * @return pattern do servlet name
   */
  public Pattern getSerlvetPattern() {
    if (this.pattern == null) {
      this.pattern = Pattern.compile(this.getServletName());
    }
    return this.pattern;
  }

}
