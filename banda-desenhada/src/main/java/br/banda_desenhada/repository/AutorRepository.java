package br.banda_desenhada.repository;

import br.banda_desenhada.model.Autor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AutorRepository {

    private final JdbcTemplate jdbc;

    public AutorRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Autor> listarTodos() {
        String sql = "SELECT * FROM autor ORDER BY nome";
        return jdbc.query(sql, (rs, rowNum) -> {
            Autor a = new Autor();
            a.setIdAutor(rs.getInt("id_autor"));
            a.setNome(rs.getString("nome"));
            a.setPseudonimo(rs.getString("pseudonimo"));
            return a;
        });
    }

    public Autor buscarPorId(int id) {
        String sql = "SELECT * FROM autor WHERE id_autor = ?";
        return jdbc.queryForObject(sql, (rs, rowNum) -> {
            Autor a = new Autor();
            a.setIdAutor(rs.getInt("id_autor"));
            a.setNome(rs.getString("nome"));
            a.setPseudonimo(rs.getString("pseudonimo"));
            return a;
        }, id);
    }

    public void criar(Autor autor) {
        String sql = "INSERT INTO autor (nome, pseudonimo) VALUES (?, ?)";
        jdbc.update(sql, autor.getNome(), autor.getPseudonimo());
    }

    public void atualizar(int id, Autor autor) {
        String sql = "UPDATE autor SET nome = ?, pseudonimo = ? WHERE id_autor = ?";
        jdbc.update(sql, autor.getNome(), autor.getPseudonimo(), id);
    }

    public void deletar(int id) {
        String sql = "DELETE FROM autor WHERE id_autor = ?";
        jdbc.update(sql, id);
    }
}
