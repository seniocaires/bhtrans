package br.com.seniocaires.maps.bhtrans.exception;

import javax.ws.rs.core.Response.Status;

public class AppException extends Exception {

  private static final long serialVersionUID = 1L;

  private Status codigo;

  public AppException(Status codigo, String mensagem) {
    super(mensagem);
    this.codigo = codigo;
  }

  public Status getCodigo() {
    return this.codigo;
  }
}
