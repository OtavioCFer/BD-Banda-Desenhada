package br.banda_desenhada.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.banda_desenhada.model.Usuario;

@Repository
public class UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(Usuario usuario) {
        String sql = """
            INSERT INTO usuario (nome, email, SenhaHash)
            VALUES (?, ?, ?)
            """;

        jdbcTemplate.update(sql,
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenhaHash());
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        String sql = """
            SELECT id_usuario,
                   nome,
                   email,
                   senha_hash,
                   criado_em
            FROM usuario
            WHERE email = ?
            """;

        List<Usuario> usuarios = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("id_usuario"));
            u.setNome(rs.getString("nome"));
            u.setEmail(rs.getString("email"));
            u.setSenhaHash(rs.getString("senha_hash"));

            Object criado = rs.getObject("criado_em");
            if (criado instanceof OffsetDateTime odt) {
                u.setCriadoEm(odt);
            }

            return u;
        }, email);

        return usuarios.stream().findFirst();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        String sql = """
            SELECT id_usuario,
                   nome,
                   email,
                   senha_hash,
                   criado_em
            FROM usuario
            WHERE id_usuario = ?
            """;

        List<Usuario> usuarios = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("id_usuario"));
            u.setNome(rs.getString("nome"));
            u.setEmail(rs.getString("email"));
            u.setSenhaHash(rs.getString("senha_hash"));

            Object criado = rs.getObject("criado_em");
            if (criado instanceof OffsetDateTime odt) {
                u.setCriadoEm(odt);
            }

            return u;
        }, id);

        return usuarios.stream().findFirst();
    }
}
