package br.banda_desenhada.service;

import br.banda_desenhada.model.Genero;
import br.banda_desenhada.repository.GeneroRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    public List<Genero> listar() {
        return generoRepository.findAll();
    }

    public void cadastrar(Genero genero) {
        generoRepository.save(genero);
    }

    public void atualizar(Genero genero) {
        generoRepository.update(genero);
    }

    public void excluir(Integer id) {
        generoRepository.delete(id);
    }

    public Genero buscarPorId(Integer id) {
        return generoRepository.findById(id);
    }
}