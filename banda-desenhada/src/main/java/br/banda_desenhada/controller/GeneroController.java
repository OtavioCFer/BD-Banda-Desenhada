package br.banda_desenhada.controller;

import br.banda_desenhada.model.Genero;
import br.banda_desenhada.service.GeneroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("generos", generoService.listar());
        return "listar-generos";
    }

    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("genero", new Genero());
        return "cadastrar-genero";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@ModelAttribute Genero genero) {
        generoService.cadastrar(genero);
        return "redirect:/generos";
    }
}