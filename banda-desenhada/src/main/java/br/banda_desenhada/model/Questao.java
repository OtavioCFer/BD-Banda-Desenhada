package br.banda_desenhada.model;

public class Questao {

    private Long idQuestao;
    private String enunciado;
    private String tipo;
    private String respostaCorreta;

    public Questao() {
    }

    public Questao(Long idQuestao, String enunciado, String tipo) {
        this.idQuestao = idQuestao;
        this.enunciado = enunciado;
        this.tipo = tipo;
    }

    public Long getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Long idQuestao) {
        this.idQuestao = idQuestao;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRespostaCorreta() {
    return respostaCorreta;
    }

    public void setRespostaCorreta(String respostaCorreta) {
    this.respostaCorreta = respostaCorreta;
    }

}
