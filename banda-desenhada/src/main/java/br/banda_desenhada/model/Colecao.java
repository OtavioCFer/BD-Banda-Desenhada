package br.banda_desenhada.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class Colecao {

    private Integer idColecao;
    private Integer idUsuario;
    private Integer idQuadrinho;
    private String status; 
    private LocalDate dataLido;
    private OffsetDateTime dataAdicionado;

    public Integer getIdColecao() {
        return idColecao;
    }

    public void setIdColecao(Integer idColecao) {
        this.idColecao = idColecao;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataLido() {
        return dataLido;
    }

    public void setDataLido(LocalDate dataLido) {
        this.dataLido = dataLido;
    }

    public OffsetDateTime getDataAdicionado() {
        return dataAdicionado;
    }

    public void setDataAdicionado(OffsetDateTime dataAdicionado) {
        this.dataAdicionado = dataAdicionado;
    }
}
