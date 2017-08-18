package br.com.seniocaires.maps.bhtrans.util;

import java.util.Map;

public class Intinerario {

  private String nome;

  private Map<Integer, String> pontos;

  public Intinerario(String nome, Map<Integer, String> pontos) {
    this.nome = nome;
    this.pontos = pontos;
  }

  public String getNome() {
    return this.nome;
  }

  public Map<Integer, String> getPontos() {
    return this.pontos;
  }
}
