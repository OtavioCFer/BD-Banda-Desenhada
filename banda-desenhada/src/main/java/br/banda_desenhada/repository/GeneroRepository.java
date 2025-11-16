package br.banda_desenhada.repository;

import br.banda_desenhada.model.Genero;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class GeneroRepository {

    private final JdbcTemplate jdbcTemplate;

    public GeneroRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Genero> generoMapper = (rs, rowNum) -> {
        return new Genero(
            rs.getInt("id"),
            rs.getString("nome")
        );
    };

    public List<Genero> findAll() {
        String sql = "SELECT * FROM genero ORDER BY nome";
        return jdbcTemplate.query(sql, generoMapper);
    }

    public Genero findById(Integer id) {
        String sql = "SELECT * FROM genero WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, generoMapper, id);
    }

    public void save(Genero genero) {
        String sql = "INSERT INTO genero (nome) VALUES (?)";
        jdbcTemplate.update(sql, genero.getNome());
    }

    public void update(Genero genero) {
        String sql = "UPDATE genero SET nome = ? WHERE id = ?";
        jdbcTemplate.update(sql, genero.getNome(), genero.getId());
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM genero WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}