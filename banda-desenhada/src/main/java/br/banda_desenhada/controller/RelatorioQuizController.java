package br.banda_desenhada.controller;

import br.banda_desenhada.repository.RespostaQuizRepository;
import br.banda_desenhada.repository.QuizRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/relatorios/quizzes")
public class RelatorioQuizController {

    private final RespostaQuizRepository respostaQuizRepository;
    private final QuizRepository quizRepository;

    public RelatorioQuizController(RespostaQuizRepository respostaQuizRepository,
                                   QuizRepository quizRepository) {
        this.respostaQuizRepository = respostaQuizRepository;
        this.quizRepository = quizRepository;
    }

    @GetMapping("/geral")
    public String relatorioGeral(Model model) {
        var relatorios = respostaQuizRepository.listarRelatorioGeralPorQuiz();
        model.addAttribute("relatorios", relatorios);
        return "relatorios/quiz_geral";
    }

    @GetMapping("/usuario/{idUsuario}")
    public String relatorioUsuario(@PathVariable Long idUsuario, Model model) {
        var relatorios = respostaQuizRepository.listarRelatorioPorUsuario(idUsuario);
        var evolucao = respostaQuizRepository.listarEvolucaoUsuario(idUsuario);

        model.addAttribute("idUsuario", idUsuario);
        model.addAttribute("relatorios", relatorios);
        model.addAttribute("evolucao", evolucao);

        return "relatorios/quiz_usuario";
    }
}
