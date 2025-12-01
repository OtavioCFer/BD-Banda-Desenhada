package br.banda_desenhada.repository;

import br.banda_desenhada.model.Opcao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OpcaoRepository {

    private final JdbcTemplate jdbcTemplate;

    public OpcaoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Opcao mapearOpcao(ResultSet rs, int rowNum) throws SQLException {
        Opcao opcao = new Opcao();
        opcao.setIdOpcao(rs.getLong("id_opcao"));
        opcao.setIdQuestao(rs.getLong("id_questao"));
        opcao.setLetraOpcao(rs.getString("letra_opcao"));
        opcao.setTextoOpcao(rs.getString("texto_opcao"));
        return opcao;
    }

    public void inserir(Opcao opcao) {
        String sql = """
            INSERT INTO opcao_questao (id_questao, letra_opcao, texto_opcao)
            VALUES (?, ?, ?)
            """;
        jdbcTemplate.update(sql,
                opcao.getIdQuestao(),
                opcao.getLetraOpcao(),
                opcao.getTextoOpcao());
    }

    public List<Opcao> listarPorQuestao(Long idQuestao) {
        String sql = """
            SELECT id_opcao, id_questao, letra_opcao, texto_opcao
              FROM opcao_questao
             WHERE id_questao = ?
             ORDER BY letra_opcao
            """;
        return jdbcTemplate.query(sql, this::mapearOpcao, idQuestao);
    }
    
    public void excluirPorQuestao(Long idQuestao) {
        String sql = "DELETE FROM opcao_questao WHERE id_questao = ?";
        jdbcTemplate.update(sql, idQuestao);
    }
}