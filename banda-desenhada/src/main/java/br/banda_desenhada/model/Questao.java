package br.banda_desenhada.model;

import java.util.List;

public class Questao {

    private Long idQuestao;
    private String enunciado;
    private String tipo;
    private String respostaCorreta;
    private List<Opcao> opcoes; // NOVO CAMPO: Lista para armazenar as opções

    public Questao() {
    }

    public Questao(Long idQuestao, String enunciado, String tipo, String respostaCorreta) {
        this.idQuestao = idQuestao;
        this.enunciado = enunciado;
        this.tipo = tipo;
        this.respostaCorreta = respostaCorreta;
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
    
    // NOVO Getters e Setters para a lista de opções
    public List<Opcao> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(List<Opcao> opcoes) {
        this.opcoes = opcoes;
    }

}