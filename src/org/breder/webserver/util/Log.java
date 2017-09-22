package org.breder.webserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

  private static final SimpleDateFormat FORMAT = new SimpleDateFormat(
    "dd/MM/yy kk:mm:ss");

  //	private static final PrintWriter output;
  //
  //	static {
  //		try {
  //			File outFile = new File("out.txt");
  //			if (!outFile.exists()) {
  //				new FileOutputStream(outFile).close();
  //			}
  //			output = new PrintWriter(new FileOutputStream(outFile, true));
  //		} catch (IOException e) {
  //			throw new Error(e);
  //		}
  //	}

  public void info(String project, int code, String message, Object... args) {
    print("[INFO]", project, code, message, args);
  }

  public void warning(String project, int code, String message, Object... args) {
    print("[WARNING]", project, code, message, args);
  }

  public void severe(String project, int code, String message, Object... args) {
    print("[SEVERE]", project, code, message, args);
  }

  public void error(String project, int code, String message, Object... args) {
    print("[ERROR]", project, code, message, args);
  }

  public void error(String project, int code, Throwable e) {
    StringBuilder sb = new StringBuilder();
    {
      sb.append("Exception ");
      sb.append(e.getClass().getSimpleName());
      sb.append(":");
      sb.append(e.getMessage() == null ? "" : e.getMessage());
      StackTraceElement[] traceElements =
        Thread.currentThread().getStackTrace();
      for (int n = 0; n < traceElements.length; n++) {
        sb.append(traceElements[n].toString());
        if (n != traceElements.length - 1) {
          sb.append("\n");
        }
      }
    }
    if (e.getCause() != null && !e.getCause().equals(e)) {
      e = e.getCause();
      sb.append("\nCaused ");
      sb.append(e.getClass().getSimpleName());
      sb.append(":");
      sb.append(e.getMessage() == null ? "" : e.getMessage());
      StackTraceElement[] traceElements = e.getStackTrace();
      for (int n = 0; n < traceElements.length; n++) {
        sb.append(traceElements[n].toString());
        if (n != traceElements.length - 1) {
          sb.append("\n");
        }
      }
    }
    print("[ERROR]", project, code, sb.toString());
  }

  protected void print(String type, String project, int code, String message,
    Object... args) {
    Thread thread = Thread.currentThread();
    StackTraceElement stack = thread.getStackTrace()[getStackMethodIndex()];
    StringBuilder sb = new StringBuilder();
    sb.append(type);
    sb.append(" ");
    sb.append(FORMAT.format(new Date()));
    sb.append(" ");
    sb.append(project);
    sb.append("(");
    sb.append(code);
    sb.append(") ");
    sb.append(thread.getName());
    sb.append("(");
    sb.append(thread.getId());
    sb.append(") ");
    sb.append(stack.getFileName().subSequence(0,
      stack.getFileName().length() - ".java".length()));
    sb.append(".");
    sb.append(stack.getMethodName());
    sb.append("(");
    sb.append(stack.getLineNumber());
    sb.append("):\n");
    if (args != null && args.length > 0) {
      sb.append(String.format(message, args));
    }
    else {
      sb.append(message);
    }
    send(sb.toString());
  }

  protected void send(String text) {
    //		System.out.println(text);
    //		output.println(text);
    //		output.flush();
  }

  protected int getStackMethodIndex() {
    return 3;
  }

}
