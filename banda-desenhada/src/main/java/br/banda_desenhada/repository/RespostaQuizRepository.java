package br.banda_desenhada.repository;

import br.banda_desenhada.model.RespostaQuiz;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class RespostaQuizRepository {

    private final JdbcTemplate jdbcTemplate;

    public RespostaQuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RespostaQuiz mapearResposta(ResultSet rs, int rowNum) throws SQLException {
        RespostaQuiz r = new RespostaQuiz();
        r.setIdResposta(rs.getLong("id_resposta"));
        r.setIdQuiz(rs.getLong("id_quiz"));
        r.setIdQuestao(rs.getLong("id_questao"));
        r.setIdUsuario(rs.getLong("id_usuario"));
        r.setRespostaTexto(rs.getString("resposta_texto"));
        r.setRespostaOpcao(rs.getString("resposta_opcao"));

        boolean isNullCorreta = rs.getObject("correta") == null;
        if (!isNullCorreta) {
            r.setCorreta(rs.getBoolean("correta"));
        }

        var ts = rs.getTimestamp("data_resposta");
        if (ts != null) {
            r.setDataResposta(ts.toInstant().atOffset(OffsetDateTime.now().getOffset()));
        }

        return r;
    }

    public void salvarResposta(RespostaQuiz resposta) {
        String sql = """
            INSERT INTO resposta_quiz (
                id_quiz, id_questao, id_usuario,
                resposta_texto, resposta_opcao, correta
            )
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        jdbcTemplate.update(sql,
                resposta.getIdQuiz(),
                resposta.getIdQuestao(),
                resposta.getIdUsuario(),
                resposta.getRespostaTexto(),
                resposta.getRespostaOpcao(),
                resposta.getCorreta());
    }

    public List<RespostaQuiz> listarPorQuizEUsuario(Long idQuiz, Long idUsuario) {
        String sql = """
            SELECT id_resposta, id_quiz, id_questao, id_usuario,
                   resposta_texto, resposta_opcao, correta, data_resposta
              FROM resposta_quiz
             WHERE id_quiz = ?
               AND id_usuario = ?
             ORDER BY id_resposta
            """;
        return jdbcTemplate.query(sql, this::mapearResposta, idQuiz, idUsuario);
    }

    public Double calcularTaxaAcertoQuiz(Long idQuiz) {
        String sql = """
            SELECT AVG(CASE WHEN correta = TRUE THEN 1.0 ELSE 0.0 END)
              FROM resposta_quiz
             WHERE id_quiz = ?
               AND correta IS NOT NULL
            """;
        return jdbcTemplate.queryForObject(sql, Double.class, idQuiz);
    }
}