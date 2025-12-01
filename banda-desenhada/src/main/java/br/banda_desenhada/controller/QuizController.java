package br.banda_desenhada.controller;

import br.banda_desenhada.model.Quiz;
import br.banda_desenhada.model.Questao;
import br.banda_desenhada.repository.QuizRepository;
import br.banda_desenhada.repository.QuestaoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizRepository quizRepository;
    private final QuestaoRepository questaoRepository;

    public QuizController(QuizRepository quizRepository, QuestaoRepository questaoRepository) {
        this.quizRepository = quizRepository;
        this.questaoRepository = questaoRepository;
    }

    // LISTAR QUIZZES
    @GetMapping
    public String listarQuizzes(Model model) {
        List<Quiz> quizzes = quizRepository.listarTodos();
        model.addAttribute("quizzes", quizzes);
        return "quiz/lista";
    }

    // FORM NOVO QUIZ
    @GetMapping("/novo")
    public String novoQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "quiz/form";
    }

    // SALVAR NOVO QUIZ
    @PostMapping
    public String salvarNovoQuiz(@ModelAttribute Quiz quiz) {
        quizRepository.inserir(quiz);
        return "redirect:/quizzes";
    }

    // REMOVER QUIZ
    @PostMapping("/{idQuiz}/remover")
    public String removerQuiz(@PathVariable Long idQuiz) {
        quizRepository.excluir(idQuiz);
        return "redirect:/quizzes";
    }

    // CONFIGURAR QUESTÕES (NOVO CENTRO DE GERENCIAMENTO UNIFICADO)
    @GetMapping("/{idQuiz}/configurar-questoes")
    public String configurarQuestoes(@PathVariable Long idQuiz, Model model) {

        Quiz quiz = quizRepository.buscarPorId(idQuiz);
        List<Questao> todasQuestoes = questaoRepository.listarTodas();
        List<Questao> questoesDoQuiz = quizRepository.listarQuestoesDoQuiz(idQuiz);

        Set<Long> idsQuestoesDoQuiz = questoesDoQuiz.stream()
                .map(Questao::getIdQuestao)
                .collect(Collectors.toSet());

        // Passa o objeto QUIZ completo para que Título/Descrição sejam editáveis no template
        model.addAttribute("quiz", quiz);
        model.addAttribute("todasQuestoes", todasQuestoes);
        model.addAttribute("idsQuestoesDoQuiz", idsQuestoesDoQuiz);

        return "quiz/configurar_questoes";
    }

    // SALVAR ALTERAÇÕES (Método unificado para salvar Metadata e Associações)
    @PostMapping("/{idQuiz}/salvar-questoes")
    public String salvarQuestoesDoQuiz(
            @PathVariable Long idQuiz,
            @ModelAttribute Quiz quiz, // 1. RECEBE TÍTULO/DESCRIÇÃO ATUALIZADOS
            @RequestParam(name = "idsQuestoesSelecionadas", required = false)
            List<Long> idsQuestoesSelecionadas) {

        // 1. ATUALIZA OS METADADOS (Título e Descrição)
        quiz.setIdQuiz(idQuiz);
        quizRepository.atualizar(quiz); 

        // 2. ATUALIZA AS ASSOCIAÇÕES DE QUESTÕES
        quizRepository.removerTodasQuestoesDoQuiz(idQuiz);

        if (idsQuestoesSelecionadas != null) {
            for (Long idQuestao : idsQuestoesSelecionadas) {
                quizRepository.adicionarQuestaoAoQuiz(idQuiz, idQuestao);
            }
        }

        return "redirect:/quizzes";
    }
}