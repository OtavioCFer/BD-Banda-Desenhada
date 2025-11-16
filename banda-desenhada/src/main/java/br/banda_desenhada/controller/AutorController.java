package br.banda_desenhada.controller;

import br.banda_desenhada.model.Autor;
import br.banda_desenhada.service.AutorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Autor> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Autor buscar(@PathVariable int id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public void criar(@RequestBody Autor autor) {
        service.criar(autor);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable int id, @RequestBody Autor autor) {
        service.atualizar(id, autor);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable int id) {
        service.deletar(id);
    }
}
