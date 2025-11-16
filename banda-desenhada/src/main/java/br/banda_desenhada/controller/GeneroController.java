package br.banda_desenhada.controller;

import br.banda_desenhada.model.Genero;
import br.banda_desenhada.service.GeneroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService service;

    public GeneroController(GeneroService service) {
        this.service = service;
    }

    // Redireciona /generos para a lista
    @GetMapping
    public String raiz() {
        return "redirect:/generos/listar-generos";
    }

    // Listar gêneros
    @GetMapping("/listar-generos")
    public String listarGeneros(Model model) {
        model.addAttribute("generos", service.listar());
        return "listar-generos";
    }

    // Formulário de cadastro
    @GetMapping("/cadastrar-genero")
    public String cadastrarGenero(Model model) {
        model.addAttribute("genero", new Genero());
        return "cadastrar-genero";
    }

    // Salvar novo gênero
    @PostMapping("/cadastrar-genero")
    public String salvarGenero(@ModelAttribute Genero genero) {
        service.cadastrar(genero);
        return "redirect:/generos/listar-generos";
    }

    // Remover gênero
    @GetMapping("/deletar/{id}")
    public String deletarGenero(@PathVariable Integer id) {
        service.deletar(id);
        return "redirect:/generos/listar-generos";
    }

    // =========================
    // ROTAS API REST (JSON)
    // =========================

    @GetMapping("/api")
    @ResponseBody
    public List<Genero> listarTodos() {
        return service.listar();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public Genero buscar(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping("/api")
    @ResponseBody
    public void criar(@RequestBody Genero genero) {
        service.cadastrar(genero);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public void atualizar(@PathVariable Integer id, @RequestBody Genero genero) {
        service.atualizar(id, genero);
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}