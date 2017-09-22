package org.breder.webserver.webserver.request.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.breder.webserver.util.FlushableGZIPOutputStream;

public class Resource {

  private final String path;

  private File file;

  private long modified;

  /**
   * Construtor padrão
   * 
   * @param path
   */
  public Resource(String path) {
    if (path.contains("..")) {
      throw new IllegalArgumentException();
    }
    while (path.startsWith("/")) {
      path = path.substring(1);
    }
    this.path = path;
    this.modified = this.getFile().lastModified();
  }

  public boolean exists() {
    return getFile().exists();
  }

  public long getLastModified() {
    return this.modified;
  }

  /**
   * @return the modified
   */
  public long getModified() {
    return modified;
  }

  protected File getFile() {
    if (this.file == null) {
      this.file = new File("pub", path);
    }
    return file;
  }

  public byte[] getGZipBytes() throws IOException {
    File file = this.getFile();
    if (!file.exists()) {
      return null;
    }
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    FlushableGZIPOutputStream gzip = new FlushableGZIPOutputStream(output);
    FileInputStream finput = new FileInputStream(file);
    try {
      for (int n; ((n = finput.read()) != -1);) {
        gzip.write(n);
      }
    }
    finally {
      finput.close();
    }
    gzip.close();
    return output.toByteArray();
  }

  public String toString() {
    return this.path;
  }

}
