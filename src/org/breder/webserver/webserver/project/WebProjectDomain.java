package org.breder.webserver.webserver.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebProjectDomain<E> {

  private final Map<String, E> domains = new HashMap<String, E>();

  private final List<Pattern> names = new ArrayList<Pattern>();

  public E get(String servlet) {
    for (Pattern name : names) {
      Matcher matcher = name.matcher(servlet);
      if (matcher.find()) {
        return domains.get(name.toString());
      }
    }
    return null;
  }

  public void add(String name, E servlet) {
    this.domains.put(name, servlet);
    this.names.add(Pattern.compile(name));
  }

}
