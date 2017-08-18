package br.com.seniocaires.maps.bhtrans.util;

public class Intinerario {

  private String nome;

  private String[] pontos;

  public Intinerario(String nome, String[] pontos) {
    this.nome = nome;
    this.pontos = pontos;
  }

  public String getNome() {
    return this.nome;
  }

  public String[] getPontos() {
    return this.pontos;
  }
}
