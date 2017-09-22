package org.breder.webserver.util;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

public class MemoryOutputStream extends OutputStream {

  protected byte buf[];

  protected int count;

  public MemoryOutputStream() {
    this(32);
  }

  public MemoryOutputStream(int size) {
    if (size < 0) {
      throw new IllegalArgumentException("Negative initial size: " + size);
    }
    buf = new byte[size];
  }

  public void write(int b) {
    int newcount = count + 1;
    if (newcount > buf.length) {
      buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
    }
    buf[count] = (byte) b;
    count = newcount;
  }

  public int size() {
    return count;
  }

  public void write(byte b[], int off, int len) {
    if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length)
      || ((off + len) < 0)) {
      throw new IndexOutOfBoundsException();
    }
    else if (len == 0) {
      return;
    }
    int newcount = count + len;
    if (newcount > buf.length) {
      buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
    }
    System.arraycopy(b, off, buf, count, len);
    count = newcount;
  }

  public void reset() {
    count = 0;
  }

  public byte toByteArray()[] {
    return Arrays.copyOf(buf, count);
  }

  public String toString() {
    return new String(buf, 0, count);
  }

  public String toUTFString() {
    return new String(buf, 0, count, Charset.forName("utf-8"));
  }

  public byte byteAt(int index) {
    return this.buf[index];
  }

}
