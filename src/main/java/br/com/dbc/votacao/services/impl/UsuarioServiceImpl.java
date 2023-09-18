package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.dtos.UsuarioDto;
import br.com.dbc.votacao.enums.StatusAssociado;
import br.com.dbc.votacao.enums.TipoUsuario;
import br.com.dbc.votacao.models.Associado;
import br.com.dbc.votacao.models.Usuario;
import br.com.dbc.votacao.repositories.UsuarioRepository;
import br.com.dbc.votacao.services.AssociadoService;
import br.com.dbc.votacao.services.UsuarioService;
import br.com.dbc.votacao.utils.CpfValidator;
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
    @Autowired
    private CpfValidator cpfValidator;
    @Autowired
    AssociadoService associadoService;

    @Override
    public boolean existsUsuarioByNomeDoUsuario(String userName) {
        return usuarioRepository.existsUsuarioByNomeDoUsuario(userName);
    }

    @Override
    public ResponseEntity<Object> salvarUsuario(UsuarioDto usuarioDto) {
        if (existsUsuarioByNomeDoUsuario(usuarioDto.getNomeDoUsuario())) {
            usuarioDto.setMensagem("Usuario já cadastrado.");
            usuarioDto.setSenha(null);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);
        } else {
            try {
                if(cpfValidator.isValid(usuarioDto.getCpf())) {
                    var usuario = new Usuario();
                    BeanUtils.copyProperties(usuarioDto, usuario);
                    usuario.setTipoUsuario(TipoUsuario.ASSOC);
                    usuario.setDataCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
                    usuario.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
                    usuarioDto.setMensagem("Usuário cadastrado com sucesso.");
                    usuarioRepository.save(usuario);
                    usuarioDto.setSenha(null);
                    criarAssociado(usuarioDto);
                    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
                } else {
                    usuarioDto.setMensagem("CPF inválido!");
                    usuarioDto.setSenha(null);
                    return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.CONTINUE).body(e.getMessage());
            }
        }
    }

    private void criarAssociado(UsuarioDto usuarioDto) {
        associadoService.salvarAssociado(usuarioDto);
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
            associadoService.deletarAssociado(encontrarUsuarioPeloNomeDoUsuario(nomeDoUsuario).get().getCpf());
            return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    @Override
    public UsuarioDto converterUsuarioParaUsuarioDto(Usuario usuario) {
        return new UsuarioDto(usuario.getNomeDoUsuario());
    }
}
