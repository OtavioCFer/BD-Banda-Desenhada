package br.banda_desenhada.repository;

import br.banda_desenhada.model.Genero;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GeneroRepository {

    private final JdbcTemplate jdbcTemplate;

    public GeneroRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Genero> findAll() {
        String sql = "SELECT id_genero, nome FROM genero ORDER BY nome";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Genero g = new Genero();
            g.setIdGenero(rs.getInt("id_genero"));
            g.setNome(rs.getString("nome"));
            return g;
        });
    }

    public Genero findById(Integer id) {
        String sql = "SELECT id_genero, nome FROM genero WHERE id_genero = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Genero g = new Genero();
            g.setIdGenero(rs.getInt("id_genero"));
            g.setNome(rs.getString("nome"));
            return g;
        }, id);
}


    public void save(Genero genero) {
        String sql = "INSERT INTO genero (nome) VALUES (?)";
        jdbcTemplate.update(sql, genero.getNome());
    }

    public void update(Genero genero) {
        String sql = "UPDATE genero SET nome = ? WHERE id_genero = ?";
        jdbcTemplate.update(sql, genero.getNome(), genero.getIdGenero());
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM genero WHERE id_genero = ?";
        jdbcTemplate.update(sql, id);
    }
}