package br.banda_desenhada.model;

public class Opcao {
    private Long idOpcao;
    private Long idQuestao;
    private String letraOpcao; // Ex: A, B, C
    private String textoOpcao; // Ex: Homem-Aranha

    // Getters e Setters
    public Long getIdOpcao() {
        return idOpcao;
    }
    public void setIdOpcao(Long idOpcao) {
        this.idOpcao = idOpcao;
    }
    public Long getIdQuestao() {
        return idQuestao;
    }
    public void setIdQuestao(Long idQuestao) {
        this.idQuestao = idQuestao;
    }
    public String getLetraOpcao() {
        return letraOpcao;
    }
    public void setLetraOpcao(String letraOpcao) {
        this.letraOpcao = letraOpcao;
    }
    public String getTextoOpcao() {
        return textoOpcao;
    }
    public void setTextoOpcao(String textoOpcao) {
        this.textoOpcao = textoOpcao;
    }
}