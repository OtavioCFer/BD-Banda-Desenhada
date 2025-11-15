package br.banda_desenhada.model;

import java.time.OffsetDateTime;

public class Usuario {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String SenhaHash;
    private OffsetDateTime criadoEm;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return SenhaHash;
    }

    public void setSenhaHash(String SenhaHash) {
        this.SenhaHash = SenhaHash;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(OffsetDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
