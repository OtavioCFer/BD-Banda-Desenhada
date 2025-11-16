package br.banda_desenhada.controller;

import br.banda_desenhada.model.Avaliacao;
import br.banda_desenhada.repository.AvaliacaoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoController(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @GetMapping("/cadastrar/{idQuadrinho}")
    public String mostrarFormulario(@PathVariable Integer idQuadrinho, Model model) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setIdQuadrinho(idQuadrinho);

        avaliacao.setIdUsuario(1);

        model.addAttribute("avaliacao", avaliacao);
        return "avaliacoes/cadastrar-avaliacao";
    }

    @PostMapping("/cadastrar")
    public String salvar(@ModelAttribute Avaliacao avaliacao) {
        if (avaliacao.getIdUsuario() == null) {
            avaliacao.setIdUsuario(1); 
        }
        avaliacaoRepository.salvar(avaliacao);
        return "redirect:/quadrinhos";
    }
}
