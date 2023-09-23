package br.com.dbc.votacao.repositories;

import br.com.dbc.votacao.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsUsuarioByEmail(String email);

    Optional<Usuario> findUsuarioByEmail(String email);

}
