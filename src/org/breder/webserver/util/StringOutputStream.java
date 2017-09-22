package org.breder.webserver.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Escreve na saída em UTF8
 * 
 * @author Tecgraf
 */
public class StringOutputStream extends OutputStream {

  /** Saída */
  private final OutputStream output;

  /**
   * @param output
   */
  public StringOutputStream(OutputStream output) {
    this.output = output;
  }

  /**
   * Escreve um conteúdo
   * 
   * @param text
   * @throws IOException
   */
  public void append(String text) throws IOException {
    int size = text.length();
    for (int n = 0; n < size; n++) {
      char c = text.charAt(n);
      if (c <= 0x7F) {
        output.write(c);
      }
      else if (c <= 0x7FF) {
        output.write(((c >> 6) & 0x1F) + 0xC0);
        output.write((c & 0x3F) + 0x80);
      }
      else {
        output.write(((c >> 12) & 0xF) + 0xE0);
        output.write(((c >> 6) & 0x3F) + 0x80);
        output.write((c & 0x3F) + 0x80);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(int n) throws IOException {
    output.write(n);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {
    this.output.close();
  }

}
