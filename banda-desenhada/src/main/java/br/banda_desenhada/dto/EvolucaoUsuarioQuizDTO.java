package br.banda_desenhada.dto;

import java.time.LocalDate;

public class EvolucaoUsuarioQuizDTO {

    private LocalDate dia;
    private double percentualAcerto; 
    private int totalRespostas;

    public EvolucaoUsuarioQuizDTO(LocalDate dia, double percentualAcerto, int totalRespostas) {
        this.dia = dia;
        this.percentualAcerto = percentualAcerto;
        this.totalRespostas = totalRespostas;
    }

    public LocalDate getDia() {
        return dia;
    }

    public double getPercentualAcerto() {
        return percentualAcerto;
    }

    public int getTotalRespostas() {
        return totalRespostas;
    }
}
