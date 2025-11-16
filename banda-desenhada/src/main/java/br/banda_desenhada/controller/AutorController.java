package br.banda_desenhada.controller;

import br.banda_desenhada.model.Autor;
import br.banda_desenhada.service.AutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    // =========================
    // ROTAS PARA PÁGINAS THYMELEAF
    // =========================

    // Redireciona /autores para a lista
    @GetMapping
    public String raiz() {
        return "redirect:/autores/listar-autores";
    }

    // Listar autores
    @GetMapping("/listar-autores")
    public String listarAutores(Model model) {
        model.addAttribute("autores", service.listarTodos());
        return "autores/listar-autores"; // subpasta autores
    }

    // Formulário de cadastro
    @GetMapping("/cadastrar-autor")
    public String cadastrarAutor(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores/cadastrar-autor"; // subpasta autores
    }

    // Salvar novo autor
    @PostMapping("/cadastrar-autor")
    public String salvarAutor(@ModelAttribute Autor autor) {
        service.criar(autor);
        return "redirect:/autores/listar-autores";
    }

    // Remover autor
    @GetMapping("/deletar/{id}")
    public String deletarAutor(@PathVariable int id) {
        service.deletar(id);
        return "redirect:/autores/listar-autores";
    }

    // =========================
    // ROTAS API REST (JSON)
    // =========================

    @GetMapping("/api")
    @ResponseBody
    public List<Autor> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public Autor buscar(@PathVariable int id) {
        return service.buscarPorId(id);
    }

    @PostMapping("/api")
    @ResponseBody
    public void criar(@RequestBody Autor autor) {
        service.criar(autor);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public void atualizar(@PathVariable int id, @RequestBody Autor autor) {
        service.atualizar(id, autor);
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public void deletar(@PathVariable int id) {
        service.deletar(id);
    }
}