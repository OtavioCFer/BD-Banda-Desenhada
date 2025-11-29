package br.banda_desenhada.model;

import java.time.OffsetDateTime;

public class RespostaQuiz {

    private Long idResposta;
    private Long idQuiz;
    private Long idQuestao;
    private Long idUsuario;
    private String respostaTexto;
    private String respostaOpcao;
    private Boolean correta;
    private OffsetDateTime dataResposta;

    public RespostaQuiz() {
    }

    public RespostaQuiz(Long idResposta, Long idQuiz, Long idQuestao, Long idUsuario,
                        String respostaTexto, String respostaOpcao,
                        Boolean correta, OffsetDateTime dataResposta) {
        this.idResposta = idResposta;
        this.idQuiz = idQuiz;
        this.idQuestao = idQuestao;
        this.idUsuario = idUsuario;
        this.respostaTexto = respostaTexto;
        this.respostaOpcao = respostaOpcao;
        this.correta = correta;
        this.dataResposta = dataResposta;
    }

    public Long getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(Long idResposta) {
        this.idResposta = idResposta;
    }

    public Long getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(Long idQuiz) {
        this.idQuiz = idQuiz;
    }

    public Long getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Long idQuestao) {
        this.idQuestao = idQuestao;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRespostaTexto() {
        return respostaTexto;
    }

    public void setRespostaTexto(String respostaTexto) {
        this.respostaTexto = respostaTexto;
    }

    public String getRespostaOpcao() {
        return respostaOpcao;
    }

    public void setRespostaOpcao(String respostaOpcao) {
        this.respostaOpcao = respostaOpcao;
    }

    public Boolean getCorreta() {
        return correta;
    }

    public void setCorreta(Boolean correta) {
        this.correta = correta;
    }

    public OffsetDateTime getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(OffsetDateTime dataResposta) {
        this.dataResposta = dataResposta;
    }
}
