package br.banda_desenhada.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.banda_desenhada.model.Quadrinho;
import br.banda_desenhada.repository.QuadrinhoRepository;

@Controller
@RequestMapping("/quadrinhos")
public class QuadrinhoController {

    private final QuadrinhoRepository quadrinhoRepository;

    public QuadrinhoController(QuadrinhoRepository quadrinhoRepository) {
        this.quadrinhoRepository = quadrinhoRepository;
    }

    @GetMapping
    public String listarQuadrinhos(Model model) {
        List<Quadrinho> quadrinhos = quadrinhoRepository.listarTodos();
        model.addAttribute("quadrinhos", quadrinhos);
        return "quadrinhos/listar-quadrinhos"; 
    }

    @GetMapping("/cadastrar-quadrinho")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("quadrinho", new Quadrinho());
        return "quadrinhos/cadastrar-quadrinhos";
    }

    @PostMapping("/cadastrar-quadrinho")
    public String salvarQuadrinho(@ModelAttribute Quadrinho quadrinho) {
        quadrinhoRepository.salvar(quadrinho);
        return "redirect:/quadrinhos";
    }
}
