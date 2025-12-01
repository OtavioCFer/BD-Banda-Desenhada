package br.banda_desenhada.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.banda_desenhada.model.Questao;

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
            SELECT id_questao, enunciado, tipo, resposta_correta
              FROM questao
             ORDER BY id_questao
            """;
        return jdbcTemplate.query(sql, this::mapearQuestao);
    }

    public Questao buscarPorId(Long idQuestao) {
        String sql = """
            SELECT id_questao, enunciado, tipo, resposta_correta
              FROM questao
             WHERE id_questao = ?
            """;
        return jdbcTemplate.queryForObject(sql, this::mapearQuestao, idQuestao);
    }

    /**
     * CORREÇÃO FINAL: Insere a questão e recupera APENAS o ID gerado (SERIAL), 
     * resolvendo o erro "Um resultado foi retornado quando nenhum era esperado".
     */
    public void inserir(Questao questao) {
        String sql = """
            INSERT INTO questao (enunciado, tipo, resposta_correta)
            VALUES (?, ?, ?)
            """;
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        // 1. Cria o PreparedStatement, indicando a coluna 'id_questao'
        jdbcTemplate.update(connection -> {
            java.sql.PreparedStatement ps = connection.prepareStatement(
                sql, 
                new String[]{"id_questao"} // Array indicando qual coluna gerada deve ser retornada
            );
            ps.setString(1, questao.getEnunciado());
            ps.setString(2, questao.getTipo());
            ps.setString(3, questao.getRespostaCorreta());
            return ps;
        }, keyHolder);

        // 2. Seta o ID gerado de volta no objeto Questao
        if (keyHolder.getKey() != null) {
            questao.setIdQuestao(keyHolder.getKey().longValue()); 
        }
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