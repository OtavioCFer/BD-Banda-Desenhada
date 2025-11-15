package br.banda_desenhada.model;

import java.time.OffsetDateTime;

public class Usuario {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String senha_hash;
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

    public String getsenha_hash() {
        return senha_hash;
    }

    public void setsenha_hash(String senha_hash) {
        this.senha_hash = senha_hash;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(OffsetDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
