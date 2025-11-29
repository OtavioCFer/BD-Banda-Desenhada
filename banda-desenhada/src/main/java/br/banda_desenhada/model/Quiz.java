package br.banda_desenhada.model;

public class Quiz {

    private Long idQuiz;
    private String titulo;
    private String descricao;

    public Quiz() {
    }

    public Quiz(Long idQuiz, String titulo, String descricao) {
        this.idQuiz = idQuiz;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Long getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(Long idQuiz) {
        this.idQuiz = idQuiz;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
