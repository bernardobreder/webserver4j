package org.breder.webserver.webserver.request.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.breder.webserver.webserver.project.WebProject;
import org.breder.webserver.webserver.request.HttpProcessor;
import org.breder.webserver.webserver.request.RequestHeader;
import org.breder.webserver.webserver.request.exception.ParameterRequestException;

/**
 * Classe que armazena dados da requisição
 * 
 * @author bernardobreder
 * 
 */
public class HttpRequest {

  /** Unidade em Segundos */
  protected static final long SECOND_UNIT = 1000;
  /** Unidade em Minutos */
  protected static final long MINUTE_UNIT = 60 * SECOND_UNIT;
  /** Unidade em Hora */
  protected static final long HOUR_UNIT = 60 * MINUTE_UNIT;
  /** Unidade em Dia */
  protected static final long DAY_UNIT = 24 * HOUR_UNIT;
  /** Unidade em Mês */
  protected static final long MONTH_UNIT = 31 * DAY_UNIT;
  /** Unidade em Ano */
  protected static final long YEAR_UNIT = 12 * MONTH_UNIT;
  /** Processador */
  private final HttpProcessor processor;
  /** Socket da requisição */
  private final Socket socket;
  /** Cabeçalho da requisição */
  private final RequestHeader header;
  /** Cookies */
  private Map<String, String> cookie;
  /** Cookies */
  private Map<String, String> parameters;

  /**
   * Construtor
   * 
   * @param processor
   * @param socket
   * @param header
   */
  public HttpRequest(HttpProcessor processor, Socket socket,
    RequestHeader header) {
    this.processor = processor;
    this.socket = socket;
    this.header = header;
  }

  /**
   * Construtor fake
   * 
   * @param header
   * @param parameters
   */
  public HttpRequest(RequestHeader header, Map<String, String> parameters) {
    super();
    this.processor = null;
    this.socket = null;
    this.header = header;
    this.parameters = parameters;
  }

  /**
   * Verifica se a requisção é de um mobile
   * 
   * @return requisção é de um mobile
   */
  public boolean isMobile() {
    String userAgent = header.get("user-agent");
    if (userAgent == null) {
      return false;
    }
    return userAgent.toLowerCase().contains("mobile");
  }

  /**
   * Retorna o projeto
   * 
   * @return descrição do projeto
   */
  public WebProject getProject() {
    return this.processor.getProject();
  }

  /**
   * Retorna o comprimento da stream de dados
   * 
   * @return comprimento da stream de dados
   */
  public long getContentLength() {
    String value = this.header.get("content-length");
    if (value == null) {
      return 0;
    }
    return Long.parseLong(value);
  }

  /**
   * Retorna o comprimento da stream de dados
   * 
   * @return comprimento da stream de dados
   */
  public Map<String, String> getCookie() {
    if (cookie == null) {
      String cookieStr = this.header.get("cookie");
      if (cookieStr == null) {
        return null;
      }
      cookie = new HashMap<String, String>();
      if (cookieStr.length() > 0) {
        int begin = 0;
        int index = cookieStr.indexOf('&');
        while (index >= 0 || cookie.size() == 0) {
          String entry =
            cookieStr.substring(begin, index < 0 ? cookieStr.length() : index);
          int equalIndex = entry.indexOf('=');
          if (equalIndex >= 0) {
            String key = entry.substring(0, equalIndex);
            String value = entry.substring(equalIndex + 1);
            cookie.put(key, value);
          }
          else {
            break;
          }
          begin = index + 1;
          index = cookieStr.indexOf('&', begin);
        }
      }
    }
    return cookie;
  }

  /**
   * @param name
   * @return the header
   */
  public String getHeader(String name) {
    return header.get(name);
  }

  /**
   * @return the path
   */
  public String getPath() {
    String servlet = this.header.getServlet();
    if (servlet.equals("/")) {
      servlet = "index.html";
    }
    return servlet;
  }

  /**
   * Retorna o método da requisição
   * 
   * @return método da requisição
   */
  public String getMethod() {
    return this.header.getMethod();
  }

  /**
   * Retorna os parametros da forma de mapa
   * 
   * @return parametros da forma de mapa
   * @throws IOException
   */
  public Map<String, String> getParameterMap() throws IOException {
    if (this.parameters == null) {
      this.parameters = new HashMap<String, String>();
      String text;
      if (!this.header.getMethod().equals("POST")) {
        text = this.header.getQuery();
      }
      else {
        int size = Integer.parseInt(this.getHeader("content-length"));
        StringBuilder sb = new StringBuilder();
        InputStreamReader reader =
          new InputStreamReader(this.getInputStream(), "utf-8");
        for (int n = 0; n < size; n++) {
          sb.append((char) reader.read());
        }
        text = sb.toString();
      }
      if (text.length() > 0) {
        for (String split : text.split("&")) {
          int equalIndex = split.indexOf('=');
          if (equalIndex >= 0) {
            String key = split.substring(0, equalIndex);
            String value = split.substring(equalIndex + 1);
            this.parameters.put(key, value);
          }
          else {
            break;
          }
        }
      }
    }
    return this.parameters;
  }

  /**
   * Retorna o parametro da requisição
   * 
   * @param name
   * @return parametro da requisição
   * @throws IOException
   */
  public String getParameter(String name) throws IOException {
    return this.getParameterMap().get(name);
  }

  /**
   * Retorna o parametro da requisição
   * 
   * @param name
   * @return parametro da requisição
   * @throws IOException
   * @throws ParameterRequestException
   */
  public String checkParameter(String name) throws IOException,
    ParameterRequestException {
    String value = this.getParameterMap().get(name);
    if (value == null) {
      throw new ParameterRequestException("argument '" + name + "' not found");
    }
    return value;
  }

  /**
   * Retorna o parametro da requisição na forma de inteiro
   * 
   * @param name
   * @return parametro da requisição
   * @throws IOException
   * @throws ParameterRequestException
   */
  public int checkParameterAsInteger(String name) throws IOException,
    ParameterRequestException {
    return Integer.parseInt(this.checkParameter(name));
  }

  /**
   * Retorna o parametro da requisição na forma de inteiro
   * 
   * @param name
   * @return parametro da requisição
   * @throws IOException
   * @throws ParameterRequestException
   */
  public double checkParameterAsDouble(String name) throws IOException,
    ParameterRequestException {
    return Double.parseDouble(this.checkParameter(name));
  }

  /**
   * Retorna o parametro da requisição na forma de inteiro
   * 
   * @param name
   * @return parametro da requisição
   * @throws IOException
   * @throws ParameterRequestException
   */
  public long checkParameterAsLong(String name) throws IOException,
    ParameterRequestException {
    return Long.parseLong(this.checkParameter(name));
  }

  /**
   * Retorna o parametro da requisição na forma de inteiro
   * 
   * @param name
   * @return parametro da requisição
   * @throws IOException
   * @throws ParameterRequestException
   */
  public float checkParameterAsFloat(String name) throws IOException,
    ParameterRequestException {
    return Float.parseFloat(this.checkParameter(name));
  }

  /**
   * Retorna o parametro da requisição na forma de inteiro
   * 
   * @param name
   * @return parametro da requisição
   * @throws IOException
   * @throws ParameterRequestException
   */
  public String checkParameterAsString(String name) throws IOException,
    ParameterRequestException {
    return this.checkParameter(name);
  }

  /**
   * Retorna o parametro da requisição na forma de inteiro
   * 
   * @param name
   * @return parametro da requisição
   * @throws IOException
   * @throws ParameterRequestException
   */
  public String checkParameterAsStringNotEmpty(String name) throws IOException,
    ParameterRequestException {
    String value = this.checkParameter(name).trim();
    if (value.length() == 0) {
      throw new ParameterRequestException("argument '" + name + "' is empty");
    }
    return value;
  }

  /**
   * Retorna o parametro da requisição na forma de inteiro
   * 
   * @param name
   * @return parametro da requisição
   * @throws IOException
   * @throws ParameterRequestException
   */
  public Date checkParameterAsDate(String name) throws IOException,
    ParameterRequestException {
    String value = this.checkParameter(name);
    try {
      long timer = Long.parseLong(value);
      int year = (int) (timer / YEAR_UNIT);
      timer -= YEAR_UNIT * year;
      int month = (int) (timer / MONTH_UNIT);
      timer -= MONTH_UNIT * month;
      int day = (int) (timer / DAY_UNIT);
      timer -= DAY_UNIT * day;
      int hour = (int) (timer / HOUR_UNIT);
      timer -= HOUR_UNIT * hour;
      int minute = (int) (timer / MINUTE_UNIT);
      timer -= MINUTE_UNIT * minute;
      int second = (int) (timer / SECOND_UNIT);
      timer -= SECOND_UNIT * second;
      int milisecond = (int) timer;
      Calendar calendar = Calendar.getInstance();
      calendar.set(year, month, day + 1, hour, minute, second);
      calendar.set(Calendar.MILLISECOND, milisecond);
      return calendar.getTime();
    }
    catch (NumberFormatException e) {
    }
    throw new ParameterRequestException("argument '" + name
      + "' is not a long date");
  }

  /**
   * Retorna o parametro da requisição na forma de inteiro
   * 
   * @param name
   * @return parametro da requisição
   * @throws IOException
   * @throws ParameterRequestException
   */
  public boolean checkParameterAsBoolean(String name) throws IOException,
    ParameterRequestException {
    String value = this.checkParameter(name);
    if (value.equalsIgnoreCase("true")) {
      return true;
    }
    else if (value.equalsIgnoreCase("false")) {
      return false;
    }
    else if (value.equals("0")) {
      return false;
    }
    else if (value.equals("1")) {
      return true;
    }
    else if (value.equalsIgnoreCase("y") || value.equalsIgnoreCase("yes")) {
      return true;
    }
    else if (value.equalsIgnoreCase("n") || value.equalsIgnoreCase("no")) {
      return true;
    }
    try {
      return Integer.parseInt(value) != 0;
    }
    catch (NumberFormatException e) {
    }
    return false;
  }

  /**
   * Retorna a stream de entrada
   * 
   * @return stream de entrada
   * @throws IOException
   */
  public InputStream getInputStream() throws IOException {
    return this.socket.getInputStream();
  }

  /**
   * Retorna o tipo de conteúdo
   * 
   * @return tipo de conteudo
   */
  public String getContentType() {
    return this.header.get("content-type");
  }

  /**
   * Recupera a sessão ou cria um
   * 
   * @return sessão ou cria um
   */
  public String getSession() {
    Map<String, String> cookie = this.getCookie();
    if (cookie == null) {
      this.cookie = new HashMap<String, String>();
      String values =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+/";
      Random r = new Random(System.currentTimeMillis());
      StringBuilder sb = new StringBuilder();
      for (int n = 0; n < 1024; n++) {
        sb.append(values.charAt(r.nextInt(values.length())));
      }
      this.cookie.put("Session", sb.toString());
    }
    return this.cookie.get("Session");
  }

  /**
   * Indica se já foi criado uma sessão
   * 
   * @return já foi criado uma sessão
   */
  public boolean hasSession() {
    Map<String, String> cookie = this.getCookie();
    return cookie != null && cookie.get("Session") != null;
  }

}
