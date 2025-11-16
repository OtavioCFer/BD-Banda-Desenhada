package br.banda_desenhada.model;

public class Genero {

    private Integer idGenero;
    private String nome;

    public Genero() {}

    public Genero(Integer idGenero, String nome) {
        this.idGenero = idGenero;
        this.nome = nome;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}