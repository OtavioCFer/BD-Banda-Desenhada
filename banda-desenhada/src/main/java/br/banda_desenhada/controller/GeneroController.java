package br.banda_desenhada.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.banda_desenhada.model.Genero;
import br.banda_desenhada.service.GeneroService;

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
        return "generos/listar-generos";
    }

    // Formulário de cadastro
    @GetMapping("/cadastrar-genero")
    public String cadastrarGenero(Model model) {
        model.addAttribute("genero", new Genero());
        return "generos/cadastrar-genero";
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