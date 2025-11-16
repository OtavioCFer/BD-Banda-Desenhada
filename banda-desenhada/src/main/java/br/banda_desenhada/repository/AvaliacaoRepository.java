package br.banda_desenhada.repository;

import java.time.OffsetDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.banda_desenhada.model.Avaliacao;

@Repository
public class AvaliacaoRepository {

    private final JdbcTemplate jdbcTemplate;

    public AvaliacaoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(Avaliacao avaliacao) {
        String sql = """
            INSERT INTO avaliacao (
                id_usuario,
                id_quadrinho,
                roteiro,
                arte,
                cores,
                edicao,
                comentario,
                criado_em
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        OffsetDateTime criadoEm = avaliacao.getCriadoEm();
        if (criadoEm == null) {
            criadoEm = OffsetDateTime.now();
        }

        jdbcTemplate.update(sql,
                avaliacao.getIdUsuario(),
                avaliacao.getIdQuadrinho(),
                avaliacao.getRoteiro(),
                avaliacao.getArte(),
                avaliacao.getCores(),
                avaliacao.getEdicao(),
                avaliacao.getComentario(),
                criadoEm
        );
    }
}
