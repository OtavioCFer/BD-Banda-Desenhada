package br.banda_desenhada.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.banda_desenhada.model.Questao;
import br.banda_desenhada.model.Quiz;
import br.banda_desenhada.model.RespostaQuiz;
import br.banda_desenhada.repository.QuizRepository;
import br.banda_desenhada.repository.RespostaQuizRepository;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/responder-quiz")
public class ResponderQuizController {

    private final QuizRepository quizRepository;
    private final RespostaQuizRepository respostaQuizRepository;

    public ResponderQuizController(QuizRepository quizRepository, RespostaQuizRepository respostaQuizRepository) {
        this.quizRepository = quizRepository;
        this.respostaQuizRepository = respostaQuizRepository;
    }

    @GetMapping("/{idQuiz}")
    public String exibirFormularioQuiz(@PathVariable Long idQuiz,
                                       @RequestParam("idUsuario") Long idUsuario,
                                       Model model) {

        Quiz quiz = quizRepository.buscarPorId(idQuiz);
        List<Questao> questoes = quizRepository.listarQuestoesDoQuiz(idQuiz);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questoes", questoes);
        model.addAttribute("idUsuario", idUsuario);

        return "quiz/responder";
    }

    @PostMapping("/{idQuiz}")
    public String processarRespostas(@PathVariable Long idQuiz,
                                     @RequestParam("idUsuario") Long idUsuario,
                                     HttpServletRequest request) {

        // 1. CORREÇÃO: Limpar tentativas anteriores para o usuário e quiz
        respostaQuizRepository.excluirRespostasAnteriores(idQuiz, idUsuario);

        List<Questao> questoes = quizRepository.listarQuestoesDoQuiz(idQuiz);

        for (Questao questao : questoes) {
            String nomeCampo = "resposta_" + questao.getIdQuestao();
            String valor = request.getParameter(nomeCampo);

            if (valor != null && !valor.isBlank()) {
                RespostaQuiz resposta = new RespostaQuiz();
                resposta.setIdQuiz(idQuiz);
                resposta.setIdQuestao(questao.getIdQuestao());
                resposta.setIdUsuario(idUsuario);

                String valorNormalizado = valor.trim();
                String gabarito = questao.getRespostaCorreta();
                
                // Configura os campos de Texto ou Opção
                if ("TEXTO".equalsIgnoreCase(questao.getTipo())) {
                    resposta.setRespostaTexto(valor);
                    resposta.setRespostaOpcao(null);
                } else {
                    // MULTIPLA
                    resposta.setRespostaOpcao(valor);
                    resposta.setRespostaTexto(null);
                }

                // Aplica a comparação e define o flag 'correta'
                if (gabarito != null && !gabarito.isBlank()) {
                    String gabaritoNormalizado = gabarito.trim();
                    boolean correta = valorNormalizado.equalsIgnoreCase(gabaritoNormalizado);
                    resposta.setCorreta(correta);
                } else {
                    resposta.setCorreta(null);
                }
                
                respostaQuizRepository.salvarResposta(resposta);
            }
        }

        // Redireciona para a página de resultados
        return "redirect:/responder-quiz/resultado?idQuiz=" + idQuiz + "&idUsuario=" + idUsuario;
    }

    // Exibe a página de resultados
    @GetMapping("/resultado")
    public String exibirResultados(@RequestParam Long idQuiz, 
                                   @RequestParam Long idUsuario, 
                                   Model model) {
        
        Quiz quiz = quizRepository.buscarPorId(idQuiz);
        
        // Calcula a taxa de acerto 
        Double taxaAcerto = respostaQuizRepository.calcularTaxaAcertoQuiz(idQuiz, idUsuario);
        
        // Contar o número de questões que tinham um gabarito definido
        List<Questao> questoesComGabarito = quizRepository.listarQuestoesDoQuiz(idQuiz).stream()
            .filter(q -> q.getRespostaCorreta() != null && !q.getRespostaCorreta().isBlank())
            .collect(Collectors.toList());
            
        int totalQuestoes = questoesComGabarito.size();

        // Passa os dados para o template quiz/resultado.html
        // Já não precisa mais multiplicar por 100 aqui, pois o Repositório retorna 0.0 a 1.0 (ou 0.0 a 100.0, dependendo da sua preferência)
        // Usaremos o valor de 0.0 a 1.0, e a multiplicação por 100% será feita no Thymeleaf.
        model.addAttribute("quiz", quiz);
        model.addAttribute("score", (taxaAcerto != null ? taxaAcerto * 100 : 0.0));
        model.addAttribute("totalRespostas", totalQuestoes);

        return "quiz/resultado";
    }
}