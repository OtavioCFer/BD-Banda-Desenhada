package br.banda_desenhada.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.time.LocalDate;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.banda_desenhada.model.RespostaQuiz;
import br.banda_desenhada.dto.RelatorioQuizGeralDTO;
import br.banda_desenhada.dto.RelatorioQuizUsuarioDTO;
import br.banda_desenhada.dto.EvolucaoUsuarioQuizDTO;


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

    public List<RelatorioQuizGeralDTO> listarRelatorioGeralPorQuiz() {
        String sql = """
            SELECT 
                q.id_quiz,
                q.titulo,
                COALESCE(AVG(CASE WHEN r.correta THEN 1.0 ELSE 0.0 END) * 100, 0) AS percentual_acerto,
                COUNT(r.id_resposta) AS total_respostas
            FROM quiz q
            LEFT JOIN resposta_quiz r ON r.id_quiz = q.id_quiz
            GROUP BY q.id_quiz, q.titulo
            ORDER BY q.titulo
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new RelatorioQuizGeralDTO(
                rs.getLong("id_quiz"),
                rs.getString("titulo"),
                rs.getDouble("percentual_acerto"),
                rs.getInt("total_respostas")
            )
        );
    }

    public List<RelatorioQuizUsuarioDTO> listarRelatorioPorUsuario(Long idUsuario) {
        String sql = """
            SELECT 
                q.id_quiz,
                q.titulo,
                AVG(CASE WHEN r.correta THEN 1.0 ELSE 0.0 END) * 100 AS percentual_acerto,
                COUNT(r.id_resposta) AS total_respostas
            FROM resposta_quiz r
            JOIN quiz q ON q.id_quiz = r.id_quiz
            WHERE r.id_usuario = ?
            GROUP BY q.id_quiz, q.titulo
            ORDER BY q.titulo
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new RelatorioQuizUsuarioDTO(
                rs.getLong("id_quiz"),
                rs.getString("titulo"),
                rs.getDouble("percentual_acerto"),
                rs.getInt("total_respostas")
            ),
            idUsuario
        );
    }

    public List<EvolucaoUsuarioQuizDTO> listarEvolucaoUsuario(Long idUsuario) {
        String sql = """
            SELECT 
                DATE(r.data_resposta) AS dia,
                AVG(CASE WHEN r.correta THEN 1.0 ELSE 0.0 END) * 100 AS percentual_acerto,
                COUNT(r.id_resposta) AS total_respostas
            FROM resposta_quiz r
            WHERE r.id_usuario = ?
            GROUP BY DATE(r.data_resposta)
            ORDER BY dia
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new EvolucaoUsuarioQuizDTO(
                rs.getDate("dia").toLocalDate(),
                rs.getDouble("percentual_acerto"),
                rs.getInt("total_respostas")
            ),
            idUsuario
        );
    }
}