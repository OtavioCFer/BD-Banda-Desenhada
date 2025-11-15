package br.banda_desenhada.repository;

import br.banda_desenhada.model.Editora;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class EditoraRepository {

    private final JdbcTemplate jdbcTemplate;

    public EditoraRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Editora> listarTodas() {
        String sql = """
            SELECT id_editora,
                   nome,
                   pais,
                   criado_em
            FROM editora
            ORDER BY nome
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Editora e = new Editora();
            e.setIdEditora(rs.getInt("id_editora"));
            e.setNome(rs.getString("nome"));
            e.setPais(rs.getString("pais"));

            Object criado = rs.getObject("criado_em");
            if (criado instanceof OffsetDateTime odt) {
                e.setCriadoEm(odt);
            }

            return e;
        });
    }

    public Editora buscarPorId(Integer id) {
        String sql = """
            SELECT id_editora, nome, pais, criado_em
            FROM editora
            WHERE id_editora = ?
            """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Editora e = new Editora();
            e.setIdEditora(rs.getInt("id_editora"));
            e.setNome(rs.getString("nome"));
            e.setPais(rs.getString("pais"));

            Object criado = rs.getObject("criado_em");
            if (criado instanceof OffsetDateTime odt) {
                e.setCriadoEm(odt);
            }

            return e;
        }, id);
    }
}