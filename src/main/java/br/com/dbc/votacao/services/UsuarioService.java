package br.com.dbc.votacao.services;

import br.com.dbc.votacao.dtos.UsuarioDto;
import br.com.dbc.votacao.models.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsuarioService {

    boolean existsUsuarioByEmail(String nomeDoUsuario);

    @Transactional(rollbackFor=Exception.class)
    ResponseEntity<Object> salvarUsuario(UsuarioDto usuarioDto);

    Optional<Usuario> findUsuarioByEmail(String nomeDoUsuario);

    @Transactional(rollbackFor=Exception.class)
    ResponseEntity<Object> alterarSenha(String nomeDoUsuario, UsuarioDto usuarioDto);

    @Transactional(rollbackFor=Exception.class)
    ResponseEntity<Object> deletarUsuario(String nomeDoUsuario);

    UsuarioDto converterUsuarioParaUsuarioDto(Usuario usuario);
}
