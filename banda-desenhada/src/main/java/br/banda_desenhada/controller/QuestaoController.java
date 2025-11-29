package br.banda_desenhada.controller;

import br.banda_desenhada.model.Questao;
import br.banda_desenhada.repository.QuestaoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/questoes")
public class QuestaoController {

    private final QuestaoRepository questaoRepository;

    public QuestaoController(QuestaoRepository questaoRepository) {
        this.questaoRepository = questaoRepository;
    }

    @GetMapping
    public String listarQuestoes(Model model) {
        List<Questao> questoes = questaoRepository.listarTodas();
        model.addAttribute("questoes", questoes);
        return "questao/lista";
    }

    @GetMapping("/nova")
    public String novaQuestaoForm(Model model) {
        model.addAttribute("questao", new Questao());
        return "questao/form";
    }

    @PostMapping
    public String salvarNovaQuestao(@ModelAttribute Questao questao) {
        questaoRepository.inserir(questao);
        return "redirect:/questoes";
    }

    @GetMapping("/{idQuestao}/editar")
    public String editarQuestaoForm(@PathVariable Long idQuestao, Model model) {
        Questao questao = questaoRepository.buscarPorId(idQuestao);
        model.addAttribute("questao", questao);
        return "questao/form";
    }

    @PostMapping("/{idQuestao}/atualizar")
    public String atualizarQuestao(@PathVariable Long idQuestao, @ModelAttribute Questao questao) {
        questao.setIdQuestao(idQuestao);
        questaoRepository.atualizar(questao);
        return "redirect:/questoes";
    }

    @PostMapping("/{idQuestao}/remover")
    public String removerQuestao(@PathVariable Long idQuestao) {
        questaoRepository.excluir(idQuestao);
        return "redirect:/questoes";
    }
}
