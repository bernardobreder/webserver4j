package org.breder.webserver.webserver.request;

import java.util.HashMap;
import java.util.List;

import org.breder.webserver.util.StringUtil;

/**
 * Classe responsável por armazenar o cabeçalho de uma requisição.
 * 
 * @author bernardobreder
 * 
 */
public class RequestHeader extends HashMap<String, String> {

  /**
   * Construtor simples
   * 
   * @param method
   * @param servlet
   * @param protocol
   */
  public RequestHeader(String method, String servlet, String protocol) {
    this.put("Method", method);
    this.put("Protocol", protocol);
    this.put("Servlet", servlet);
  }

  /**
   * Construtor baseado em bytes
   * 
   * @param header
   */
  public RequestHeader(String header) {
    // Cria as linhas
    List<String> lines = StringUtil.lines(header);
    // Para a primeira linha
    String line0 = lines.get(0);
    String method = line0.substring(0, line0.indexOf(' '));
    String servlet =
      line0.substring(method.length() + 1, line0.indexOf(' ',
        method.length() + 1));
    String protocol = line0.substring(line0.lastIndexOf(' ') + 1);
    for (int n = 1; n < lines.size(); n++) {
      String item = lines.get(n);
      if (item.equals("")) {
        break;
      }
      String key = item.substring(0, item.indexOf(':')).toLowerCase();
      String value = item.substring(key.length() + 2);
      this.put(key, value);
    }
    this.put("Method", method);
    this.put("Protocol", protocol);
    this.put("Servlet", servlet);
  }

  /**
   * @return the method
   */
  public String getMethod() {
    return this.get("Method");
  }

  /**
   * @return the servlet
   */
  public String getServlet() {
    String value = this.get("Servlet");
    if (value == null) {
      return null;
    }
    if (value.equals("/")) {
      return "/index.html";
    }
    int index = value.indexOf('?');
    if (index >= 0) {
      value = value.substring(0, index);
    }
    return value;
  }

  /**
   * @return the servlet
   */
  public String getQuery() {
    String value = this.get("Servlet");
    if (value == null) {
      return null;
    }
    if (value.equals("/")) {
      return "/index.html";
    }
    int index = value.indexOf('?');
    if (index < 0) {
      return "";
    }
    return value.substring(index + 1);
  }

  /**
   * @return the protocol
   */
  public String getProtocol() {
    return this.get("Protocol");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.getMethod());
    sb.append(' ');
    sb.append(this.getServlet());
    sb.append(' ');
    sb.append(this.getProtocol());
    sb.append("\r\n");
    for (String key : this.keySet()) {
      sb.append(key);
      sb.append(": ");
      sb.append(this.get(key));
      sb.append("\r\n");
    }
    sb.append("\r\n");
    return sb.toString();
  }
}
