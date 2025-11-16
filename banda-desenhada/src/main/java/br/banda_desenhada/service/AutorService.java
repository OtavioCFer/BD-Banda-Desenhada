package br.banda_desenhada.service;

import br.banda_desenhada.model.Autor;
import br.banda_desenhada.repository.AutorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AutorService {

    private final AutorRepository repo;

    public AutorService(AutorRepository repo) {
        this.repo = repo;
    }

    public List<Autor> listarTodos() {
        return repo.listarTodos();
    }

    public Autor buscarPorId(int id) {
        return repo.buscarPorId(id);
    }

    public void criar(Autor autor) {
        repo.criar(autor);
    }

    public void atualizar(int id, Autor autor) {
        repo.atualizar(id, autor);
    }

    public void deletar(int id) {
        repo.deletar(id);
    }
}