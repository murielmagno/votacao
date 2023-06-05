package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.dtos.UsuarioDto;
import br.com.dbc.votacao.enums.TipoUsuario;
import br.com.dbc.votacao.models.Usuario;
import br.com.dbc.votacao.repositories.UsuarioRepository;
import br.com.dbc.votacao.services.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public boolean existsUsuarioByNomeDoUsuario(String userName) {
        return usuarioRepository.existsUsuarioByNomeDoUsuario(userName);
    }

    @Override
    public ResponseEntity<Object> salvarUsuario(UsuarioDto usuarioDto) {
        if (existsUsuarioByNomeDoUsuario(usuarioDto.getNomeDoUsuario())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario já cadastrado.");
        } else {
            try {
                var usuario = new Usuario();
                BeanUtils.copyProperties(usuarioDto, usuario);
                usuario.setTipoUsuario(TipoUsuario.ADMIN);
                usuario.setDataCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
                usuario.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
                usuarioRepository.save(usuario);
                return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.CONTINUE).body(e.getMessage());
            }
        }
    }

    @Override
    public Optional<Usuario> encontrarUsuarioPeloNomeDoUsuario(String nomeDoUsuario) {
        return usuarioRepository.findUsuarioByNomeDoUsuario(nomeDoUsuario);
    }

    @Override
    public ResponseEntity<Object> alterarSenha(String nomeDoUsuario, UsuarioDto usuarioDto) {

        if (existsUsuarioByNomeDoUsuario(nomeDoUsuario)) {
            Optional<Usuario> usuario = encontrarUsuarioPeloNomeDoUsuario(nomeDoUsuario);
            if (usuario.get().getSenha().equals(usuarioDto.getSenhaAntiga())) {
                usuario.get().setSenha(usuarioDto.getSenha());
                usuario.get().setDataAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
                usuarioRepository.save(usuario.get());
                return ResponseEntity.status(HttpStatus.OK).body("Senha alterada.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Senha errada!.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado.");
        }
    }

    @Override
    public ResponseEntity<Object> deletarUsuario(String nomeDoUsuario) {
        if (existsUsuarioByNomeDoUsuario(nomeDoUsuario)) {
            usuarioRepository.deleteById(encontrarUsuarioPeloNomeDoUsuario(nomeDoUsuario).get().getId());
            return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }
}
