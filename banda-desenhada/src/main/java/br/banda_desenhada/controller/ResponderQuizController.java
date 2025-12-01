package br.banda_desenhada.controller;

import java.util.List;
import java.util.stream.Collectors; // <-- IMPORTAÇÃO CORRIGIDA
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        List<Questao> questoes = quizRepository.listarQuestoesDoQuiz(idQuiz);
        int respostasProcessadas = 0;

        for (Questao questao : questoes) {
            String nomeCampo = "resposta_" + questao.getIdQuestao();
            String valor = request.getParameter(nomeCampo);

            if (valor != null && !valor.isBlank()) {
                RespostaQuiz resposta = new RespostaQuiz();
                resposta.setIdQuiz(idQuiz);
                resposta.setIdQuestao(questao.getIdQuestao());
                resposta.setIdUsuario(idUsuario);

                String valorNormalizado = valor.trim();

                if ("TEXTO".equalsIgnoreCase(questao.getTipo())) {
                    resposta.setRespostaTexto(valor);
                    resposta.setRespostaOpcao(null);
                    resposta.setCorreta(null);
                } else {
                    resposta.setRespostaOpcao(valor);
                    resposta.setRespostaTexto(null);

                    String gabarito = questao.getRespostaCorreta();
                    if (gabarito != null && !gabarito.isBlank()) {
                        String gabaritoNormalizado = gabarito.trim();
                        boolean correta = valorNormalizado.equalsIgnoreCase(gabaritoNormalizado);
                        resposta.setCorreta(correta);
                    } else {
                        resposta.setCorreta(null);
                    }
                }

                respostaQuizRepository.salvarResposta(resposta);
                respostasProcessadas++; // Conta quantas respostas foram de fato salvas
            }
        }
        
        // NOVO: Redireciona para a página de resultados
        return "redirect:/responder-quiz/resultado?idQuiz=" + idQuiz + "&idUsuario=" + idUsuario;
    }

    // NOVO MÉTODO: Exibe a página de resultados
    @GetMapping("/resultado")
    public String exibirResultados(@RequestParam Long idQuiz, 
                                   @RequestParam Long idUsuario, 
                                   Model model) {
        
        Quiz quiz = quizRepository.buscarPorId(idQuiz);
        
        // O método retorna um Double (taxa de acerto de 0.0 a 1.0)
        Double taxaAcerto = respostaQuizRepository.calcularTaxaAcertoQuiz(idQuiz);
        
        // Contar o número de questões que tinham um gabarito definido para fins de UX
        List<Questao> questoesComGabarito = quizRepository.listarQuestoesDoQuiz(idQuiz).stream()
            .filter(q -> q.getRespostaCorreta() != null && !q.getRespostaCorreta().isBlank())
            .collect(Collectors.toList());
            
        int totalQuestoes = questoesComGabarito.size();

        // Multiplica por 100 para obter a porcentagem e formata
        model.addAttribute("quiz", quiz);
        model.addAttribute("score", (taxaAcerto != null ? taxaAcerto * 100 : 0.0));
        model.addAttribute("totalRespostas", totalQuestoes);

        return "quiz/resultado";
    }
}