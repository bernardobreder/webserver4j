package org.breder.webserver.webserver.project;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que armazena todas as configurações dos projetos web
 * 
 * @author bernardobreder
 * 
 */
public class WebProjects {

  /** Projetos associado */
  private final Map<String, WebProject> projects =
    new HashMap<String, WebProject>();

  /**
   * Retorna o projeto root
   * 
   * @return projeto root
   */
  public WebProject get() {
    return this.projects.get(null);
  }

  /**
   * Atribui o projeto root
   * 
   * @param project
   */
  public void set(WebProject project) {
    this.projects.put(null, project);
  }

}
