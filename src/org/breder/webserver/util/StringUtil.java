package org.breder.webserver.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitário para String
 * 
 * 
 * @author Bernardo Breder
 */
public class StringUtil {

  @SuppressWarnings("deprecation")
  public static String decodeUrl(String url) {
    return URLDecoder.decode(url);
  }

  @SuppressWarnings("deprecation")
  public static String encodeUrl(String url) {
    return URLEncoder.encode(url);
  }

  /**
   * Constroi a String baseado nos bytes
   * 
   * @param bytes
   * @param offset
   * @param length
   * @return
   */
  public static String build(byte[] bytes, int offset, int length) {
    StringBuilder sb = new StringBuilder();
    for (int n = offset; n < length; n++) {
      int c = bytes[n];
      if (c <= 0x7F) {
        sb.append((char) c);
      }
      else if ((c >> 5) == 0x6) {
        int i2 = bytes[n++];
        sb.append((char) ((c & 0x1F) << 6) + (i2 & 0x3F));
      }
      else {
        int i2 = bytes[n++];
        int i3 = bytes[n++];
        sb.append((char) ((c & 0xF) << 12) + ((i2 & 0x3F) << 6) + (i3 & 0x3F));
      }
    }
    return sb.toString();
  }

  /**
   * Divide as linhas
   * 
   * @param text
   * @return
   */
  public static List<String> lines(String text) {
    List<String> list = new ArrayList<String>();
    int begin = 0;
    int index = text.indexOf('\n');
    while (index >= 0) {
      list.add(text.substring(begin, index).trim());
      begin = index + 1;
      index = text.indexOf('\n', begin);
    }
    list.add(text.substring(begin));
    return list;
  }
}