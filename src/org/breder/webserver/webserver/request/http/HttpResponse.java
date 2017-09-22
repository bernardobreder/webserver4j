package org.breder.webserver.webserver.request.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.breder.webserver.util.FlushableGZIPOutputStream;

/**
 * Classe que realiza a resposta de uma requisição http
 * 
 * 
 * @author Tecgraf
 */
public class HttpResponse {

  /** Array de bytes */
  private ByteArrayOutputStream bytes;
  /** Compactador */
  private FlushableGZIPOutputStream output;
  /** Tipo de conteúdo */
  private String contentType;
  /** Bytes */
  private byte[] bytesArray;

  /**
   * Construtor
   * 
   * @param contentType
   * @throws IOException
   */
  public HttpResponse(String contentType) throws IOException {
    this.setContentType(contentType);
    this.bytes = new ByteArrayOutputStream();
    this.output = new FlushableGZIPOutputStream(bytes);
  }

  /**
   * Construtor
   * 
   * @param contentType
   * @param bytes
   * @throws IOException
   */
  public HttpResponse(String contentType, byte[] bytes) throws IOException {
    this.setContentType(contentType);
    this.bytesArray = bytes;
  }

  /**
   * Retorna a stream de saída
   * 
   * @return stream de saída
   */
  public OutputStream getOutputStream() {
    return this.output;
  }

  /**
   * Fecha a saída
   * 
   * @throws IOException
   */
  public void close() throws IOException {
    this.output.close();
    this.bytesArray = this.bytes.toByteArray();
    this.bytes = null;
    this.output = null;
  }

  /**
   * Retorna os bytes do servlet
   * 
   * @return bytes do servlet
   */
  public byte[] getBytes() {
    return this.bytesArray;
  }

  /**
   * @return the contentType
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * @param contentType the contentType to set
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

}
