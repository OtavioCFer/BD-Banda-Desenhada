package br.banda_desenhada.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.banda_desenhada.model.Quadrinho;

@Repository
public class QuadrinhoRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuadrinhoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Quadrinho> listarTodos() {
        String sql = """
            SELECT q.id_quadrinho,
                   q.titulo,
                   q.sinopse,
                   q.numero_edicao,
                   q.ano_edicao,
                   q.paginas,
                   q.isbn,
                   q.capa_url,
                   q.criado_em,
                   q.id_editora,
                   e.nome AS nome_editora
            FROM quadrinho q
            JOIN editora e ON e.id_editora = q.id_editora
            ORDER BY q.titulo
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Quadrinho q = new Quadrinho();
            q.setIdQuadrinho(rs.getInt("id_quadrinho"));
            q.setTitulo(rs.getString("titulo"));
            q.setSinopse(rs.getString("sinopse"));
            q.setNumeroEdicao((Integer) rs.getObject("numero_edicao"));
            q.setAnoEdicao((Integer) rs.getObject("ano_edicao"));
            q.setPaginas((Integer) rs.getObject("paginas"));
            q.setIsbn(rs.getString("isbn"));
            q.setCapaUrl(rs.getString("capa_url"));

            // created_at pode ser null, então usamos getObject de forma segura
            Object criadoEmObj = rs.getObject("criado_em");
            if (criadoEmObj instanceof OffsetDateTime odt) {
                q.setCriadoEm(odt);
            }

            q.setIdEditora((Integer) rs.getObject("id_editora"));
            q.setNomeEditora(rs.getString("nome_editora"));
            return q;
        });
    }
}
