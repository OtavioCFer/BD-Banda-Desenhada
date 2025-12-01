package br.banda_desenhada.repository;

import br.banda_desenhada.model.Questao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuestaoRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuestaoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Questao mapearQuestao(ResultSet rs, int rowNum) throws SQLException {
        Questao questao = new Questao();
        questao.setIdQuestao(rs.getLong("id_questao"));
        questao.setEnunciado(rs.getString("enunciado"));
        questao.setTipo(rs.getString("tipo"));
        questao.setRespostaCorreta(rs.getString("resposta_correta"));
        return questao;
    }

    public List<Questao> listarTodas() {
        String sql = """
            SELECT id_questao, enunciado, tipo
              FROM questao
             ORDER BY id_questao
            """;
        return jdbcTemplate.query(sql, this::mapearQuestao);
    }

    public Questao buscarPorId(Long idQuestao) {
        String sql = """
            SELECT id_questao, enunciado, tipo
              FROM questao
             WHERE id_questao = ?
            """;
        return jdbcTemplate.queryForObject(sql, this::mapearQuestao, idQuestao);
    }

    public void inserir(Questao questao) {
        String sql = """
            INSERT INTO questao (enunciado, tipo, resposta_correta)
            VALUES (?, ?, ?)
            """;
        jdbcTemplate.update(sql,
                questao.getEnunciado(),
                questao.getTipo(),
                questao.getRespostaCorreta());
    }

    public void atualizar(Questao questao) {
        String sql = """
            UPDATE questao
               SET enunciado = ?,
                   tipo = ?,
                   resposta_correta = ?
             WHERE id_questao = ?
            """;
        jdbcTemplate.update(sql,
                questao.getEnunciado(),
                questao.getTipo(),
                questao.getRespostaCorreta(),
                questao.getIdQuestao());
    }

    public void excluir(Long idQuestao) {
        String sql = "DELETE FROM questao WHERE id_questao = ?";
        jdbcTemplate.update(sql, idQuestao);
    }
}
