package br.banda_desenhada.controller;

import br.banda_desenhada.repository.QuadrinhoRepository;
import br.banda_desenhada.model.Quadrinho;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class QuadrinhoController {

    private final QuadrinhoRepository quadrinhoRepository;

    public QuadrinhoController(QuadrinhoRepository quadrinhoRepository) {
        this.quadrinhoRepository = quadrinhoRepository;
    }

    @GetMapping("/quadrinhos")
    public String listarQuadrinhos(Model model) {
        List<Quadrinho> quadrinhos = quadrinhoRepository.listarTodos();
        model.addAttribute("quadrinhos", quadrinhos);
        return "listar-quadrinhos"; 
    }
}
