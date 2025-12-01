package br.banda_desenhada.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.banda_desenhada.model.Questao;
import br.banda_desenhada.model.Quiz;

@Repository
public class QuizRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Quiz mapearQuiz(ResultSet rs, int rowNum) throws SQLException {
        Quiz quiz = new Quiz();
        quiz.setIdQuiz(rs.getLong("id_quiz"));
        quiz.setTitulo(rs.getString("titulo"));
        quiz.setDescricao(rs.getString("descricao"));
        return quiz;
    }

    public List<Quiz> listarTodos() {
        String sql = """
            SELECT id_quiz, titulo, descricao
              FROM quiz
             ORDER BY id_quiz
            """;
        return jdbcTemplate.query(sql, this::mapearQuiz);
    }

    public Quiz buscarPorId(Long idQuiz) {
        String sql = """
            SELECT id_quiz, titulo, descricao
              FROM quiz
             WHERE id_quiz = ?
            """;
        return jdbcTemplate.queryForObject(sql, this::mapearQuiz, idQuiz);
    }

    public void inserir(Quiz quiz) {
        String sql = """
            INSERT INTO quiz (titulo, descricao)
            VALUES (?, ?)
            """;
        jdbcTemplate.update(sql, quiz.getTitulo(), quiz.getDescricao());
    }

    public void atualizar(Quiz quiz) {
        String sql = """
            UPDATE quiz
               SET titulo = ?,
                   descricao = ?
             WHERE id_quiz = ?
            """;
        jdbcTemplate.update(sql,
                quiz.getTitulo(),
                quiz.getDescricao(),
                quiz.getIdQuiz());
    }

    public void excluir(Long idQuiz) {
        String sql = "DELETE FROM quiz WHERE id_quiz = ?";
        jdbcTemplate.update(sql, idQuiz);
    }


    public void adicionarQuestaoAoQuiz(Long idQuiz, Long idQuestao) {
        String sql = """
            INSERT INTO quiz_questao (id_quiz, id_questao)
            VALUES (?, ?)
            """;
        jdbcTemplate.update(sql, idQuiz, idQuestao);
    }

    public void removerQuestaoDoQuiz(Long idQuiz, Long idQuestao) {
        String sql = """
            DELETE FROM quiz_questao
             WHERE id_quiz = ?
               AND id_questao = ?
            """;
        jdbcTemplate.update(sql, idQuiz, idQuestao);
    }

    public List<Questao> listarQuestoesDoQuiz(Long idQuiz) {
        String sql = """
            SELECT q.id_questao, q.enunciado, q.tipo, q.resposta_correta
              FROM questao q
              JOIN quiz_questao qq ON qq.id_questao = q.id_questao
             WHERE qq.id_quiz = ?
             ORDER BY q.id_questao
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Questao questao = new Questao();
            questao.setIdQuestao(rs.getLong("id_questao"));
            questao.setEnunciado(rs.getString("enunciado"));
            questao.setTipo(rs.getString("tipo"));
            questao.setRespostaCorreta(rs.getString("resposta_correta"));
            return questao;
        }, idQuiz);
    }

    public void removerTodasQuestoesDoQuiz(Long idQuiz) {
    String sql = "DELETE FROM quiz_questao WHERE id_quiz = ?";
    jdbcTemplate.update(sql, idQuiz);
    }

}
