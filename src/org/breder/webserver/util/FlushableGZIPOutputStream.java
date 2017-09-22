/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.breder.webserver.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

public class FlushableGZIPOutputStream extends GZIPOutputStream {

  public FlushableGZIPOutputStream(OutputStream os) throws IOException {
    super(os);
  }

  /**
   * It is used to reserve one byte of real data so that it can be used when
   * flushing the stream.
   */
  private byte[] lastByte = new byte[1];

  private boolean hasLastByte = false;

  @Override
  public void write(byte[] bytes) throws IOException {
    write(bytes, 0, bytes.length);
  }

  @Override
  public void write(byte[] bytes, int offset, int length) throws IOException {
    if (length > 0) {
      flushLastByte();
      if (length > 1) {
        super.write(bytes, offset, length - 1);
      }
      rememberLastByte(bytes[offset + length - 1]);
    }
  }

  @Override
  public void write(int i) throws IOException {
    flushLastByte();
    rememberLastByte((byte) i);
  }

  @Override
  public void finish() throws IOException {
    try {
      flushLastByte();
    }
    catch (IOException ignore) {
    }
    super.finish();
  }

  @Override
  public void close() throws IOException {
    try {
      flushLastByte();
    }
    catch (IOException ignored) {
    }
    super.close();
  }

  private void rememberLastByte(byte b) {
    lastByte[0] = b;
    hasLastByte = true;
  }

  private void flushLastByte() throws IOException {
    if (hasLastByte) {
      hasLastByte = false;
      super.write(lastByte, 0, 1);
    }
  }

  @Override
  public void flush() throws IOException {
    if (hasLastByte) {
      if (!def.finished()) {
        def.setLevel(Deflater.NO_COMPRESSION);
        flushLastByte();
        def.setLevel(Deflater.DEFAULT_COMPRESSION);
      }
    }
    out.flush();
  }

  /*
   * Keep on calling deflate until it runs dry. The default implementation only
   * does it once and can therefore hold onto data when they need to be flushed
   * out.
   */
  @Override
  protected void deflate() throws IOException {
    int len;
    do {
      len = def.deflate(buf, 0, buf.length);
      if (len > 0) {
        out.write(buf, 0, len);
      }
    } while (len != 0);
  }

}
