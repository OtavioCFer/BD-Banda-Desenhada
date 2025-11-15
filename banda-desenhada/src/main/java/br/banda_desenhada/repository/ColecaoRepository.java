package br.banda_desenhada.repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.banda_desenhada.model.Colecao;

@Repository
public class ColecaoRepository {

    private final JdbcTemplate jdbcTemplate;

    public ColecaoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void adicionarNaColecao(Integer idUsuario, Integer idQuadrinho, String status) {
        String sql = """
            INSERT INTO colecao (id_usuario, id_quadrinho, status)
            VALUES (?, ?, ?)
            ON CONFLICT (id_usuario, id_quadrinho)
            DO UPDATE SET status = EXCLUDED.status
            """;

        jdbcTemplate.update(sql, idUsuario, idQuadrinho, status);
    }

    public void marcarComoLido(Integer idUsuario, Integer idQuadrinho, LocalDate dataLido) {
        String sql = """
            UPDATE colecao
            SET status = 'LIDO',
                data_lido = ?
            WHERE id_usuario = ?
              AND id_quadrinho = ?
            """;

        jdbcTemplate.update(sql, dataLido, idUsuario, idQuadrinho);
    }

    public Optional<Colecao> buscarPorUsuarioEQuadrinho(Integer idUsuario, Integer idQuadrinho) {
        String sql = """
            SELECT id_colecao,
                   id_usuario,
                   id_quadrinho,
                   status,
                   data_lido,
                   data_adicionado
            FROM colecao
            WHERE id_usuario = ?
              AND id_quadrinho = ?
            """;

        List<Colecao> lista = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Colecao c = new Colecao();
            c.setIdColecao(rs.getInt("id_colecao"));
            c.setIdUsuario(rs.getInt("id_usuario"));
            c.setIdQuadrinho(rs.getInt("id_quadrinho"));
            c.setStatus(rs.getString("status"));

            c.setDataLido(rs.getObject("data_lido", LocalDate.class));

            Object adicionado = rs.getObject("data_adicionado");
            if (adicionado instanceof OffsetDateTime odt) {
                c.setDataAdicionado(odt);
            }

            return c;
        }, idUsuario, idQuadrinho);

        return lista.stream().findFirst();
    }

    public List<Colecao> listarPorUsuario(Integer idUsuario) {
        String sql = """
            SELECT id_colecao,
                   id_usuario,
                   id_quadrinho,
                   status,
                   data_lido,
                   data_adicionado
            FROM colecao
            WHERE id_usuario = ?
            ORDER BY data_adicionado DESC
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Colecao c = new Colecao();
            c.setIdColecao(rs.getInt("id_colecao"));
            c.setIdUsuario(rs.getInt("id_usuario"));
            c.setIdQuadrinho(rs.getInt("id_quadrinho"));
            c.setStatus(rs.getString("status"));

            c.setDataLido(rs.getObject("data_lido", LocalDate.class));

            Object adicionado = rs.getObject("data_adicionado");
            if (adicionado instanceof OffsetDateTime odt) {
                c.setDataAdicionado(odt);
            }

            return c;
        }, idUsuario);
    }
}
