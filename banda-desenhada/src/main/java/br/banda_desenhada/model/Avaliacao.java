package br.banda_desenhada.model;

import java.time.OffsetDateTime;

public class Avaliacao {

    private Integer idAvaliacao;
    private Integer idUsuario;
    private Integer idQuadrinho;
    private Short roteiro;
    private Short arte;
    private Short cores;
    private Short edicao;
    private String comentario;
    private OffsetDateTime criadoEm;

    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdQuadrinho() {
        return idQuadrinho;
    }

    public void setIdQuadrinho(Integer idQuadrinho) {
        this.idQuadrinho = idQuadrinho;
    }

    public Short getRoteiro() {
        return roteiro;
    }

    public void setRoteiro(Short roteiro) {
        this.roteiro = roteiro;
    }

    public Short getArte() {
        return arte;
    }

    public void setArte(Short arte) {
        this.arte = arte;
    }

    public Short getCores() {
        return cores;
    }

    public void setCores(Short cores) {
        this.cores = cores;
    }

    public Short getEdicao() {
        return edicao;
    }

    public void setEdicao(Short edicao) {
        this.edicao = edicao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(OffsetDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Double getNotaGeral() {
        if (roteiro == null || arte == null || cores == null || edicao == null) {
            return null;
        }
        return (roteiro + arte + cores + edicao) / 4.0;
    }
}
