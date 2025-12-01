package br.banda_desenhada.dto;

public class RelatorioQuizUsuarioDTO {

    private Long idQuiz;
    private String tituloQuiz;
    private double percentualAcerto;
    private int totalRespostas;

    public RelatorioQuizUsuarioDTO(Long idQuiz, String tituloQuiz, double percentualAcerto, int totalRespostas) {
        this.idQuiz = idQuiz;
        this.tituloQuiz = tituloQuiz;
        this.percentualAcerto = percentualAcerto;
        this.totalRespostas = totalRespostas;
    }

    public Long getIdQuiz() {
        return idQuiz;
    }

    public String getTituloQuiz() {
        return tituloQuiz;
    }

    public double getPercentualAcerto() {
        return percentualAcerto;
    }

    public int getTotalRespostas() {
        return totalRespostas;
    }
}
