package br.banda_desenhada.repository;

import br.banda_desenhada.model.RespostaQuiz;
import org.springframework.dao.EmptyResultDataAccessException;
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

    /**
     * CORREÇÃO: Calcula a taxa de acerto como uma fração (0.0 a 1.0) usando 
     * um SUM e COUNT robusto com conversão explícita (double precision).
     */
    public Double calcularTaxaAcertoQuiz(Long idQuiz) {
        String sql = """
            SELECT 
                CAST(SUM(CASE WHEN correta = TRUE THEN 1 ELSE 0 END) AS DOUBLE PRECISION) / 
                NULLIF(COUNT(correta), 0)
            FROM resposta_quiz
            WHERE id_quiz = ?
            AND correta IS NOT NULL
            """;
        
        try {
            // queryForObject retorna a média (0.0 a 1.0)
            Double resultado = jdbcTemplate.queryForObject(sql, Double.class, idQuiz);
            
            // Se o resultado for NULL (divisão por zero), retorna 0.0
            return resultado != null ? resultado : 0.0; 
        } catch (EmptyResultDataAccessException e) {
            // Caso não haja nenhuma resposta no filtro, retorna 0.0
            return 0.0;
        }
    }
    
    /**
     * NOVO MÉTODO: Exclui todas as respostas de um usuário para um Quiz.
     * Usado para garantir que a pontuação seja da última tentativa.
     */
    public void excluirRespostasAnteriores(Long idQuiz, Long idUsuario) {
        String sql = "DELETE FROM resposta_quiz WHERE id_quiz = ? AND id_usuario = ?";
        jdbcTemplate.update(sql, idQuiz, idUsuario);
    }
}