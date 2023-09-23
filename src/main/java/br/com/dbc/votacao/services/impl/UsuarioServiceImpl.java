package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.dtos.MensagensDto;
import br.com.dbc.votacao.dtos.UsuarioDto;
import br.com.dbc.votacao.enums.TipoUsuario;
import br.com.dbc.votacao.models.Usuario;
import br.com.dbc.votacao.repositories.UsuarioRepository;
import br.com.dbc.votacao.services.AssociadoService;
import br.com.dbc.votacao.services.UsuarioService;
import br.com.dbc.votacao.utils.CpfValidator;
import jakarta.transaction.Transactional;
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

    public static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    private CpfValidator cpfValidator;
    @Autowired
    AssociadoService associadoService;

    @Override
    public boolean existsUsuarioByEmail(String email) {
        return usuarioRepository.existsUsuarioByEmail(email);
    }

    @Override
    public ResponseEntity<Object> salvarUsuario(UsuarioDto usuarioDto) {
        if (existsUsuarioByEmail(usuarioDto.getEmail())) {
            usuarioDto.setMensagem("Usuario já cadastrado.");
            usuarioDto.setSenha(null);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);
        } else {
            try {
                if (cpfValidator.isValid(usuarioDto.getCpf())) {
                    var usuario = new Usuario();
                    BeanUtils.copyProperties(usuarioDto, usuario);
                    usuario.setTipoUsuario(TipoUsuario.ASSOC);
                    usuario.setDataCriacao(LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
                    usuario.setDataAtualizacao(LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
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
    public Optional<Usuario> findUsuarioByEmail(String email) {
        return usuarioRepository.findUsuarioByEmail(email);
    }

    @Override
    public ResponseEntity<Object> alterarSenha(String nomeDoUsuario, UsuarioDto usuarioDto) {
        MensagensDto mensagensDto = new MensagensDto();
        if (existsUsuarioByEmail(nomeDoUsuario)) {
            Optional<Usuario> usuario = findUsuarioByEmail(nomeDoUsuario);
            if (usuario.get().getSenha().equals(usuarioDto.getSenhaAntiga())) {
                usuario.get().setSenha(usuarioDto.getSenha());
                usuario.get().setDataAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
                usuarioRepository.save(usuario.get());
                mensagensDto.setMensagem("Senha alterada.");
                return ResponseEntity.status(HttpStatus.OK).body(mensagensDto);
            } else {
                mensagensDto.setMensagem("Senha errada!.");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(mensagensDto);
            }
        } else {
            mensagensDto.setMensagem("Usuario não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagensDto);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> deletarUsuario(String nomeDoUsuario) {
        MensagensDto mensagensDto = new MensagensDto();
        if (existsUsuarioByEmail(nomeDoUsuario)) {
            Optional<Usuario> usuario = findUsuarioByEmail(nomeDoUsuario);
            if (usuario.isPresent()) {
                associadoService.deletarAssociado(usuario.get().getCpf());
                usuarioRepository.deleteById(usuario.get().getId());
                mensagensDto.setMensagem("Usuario deletado");
                return ResponseEntity.status(HttpStatus.OK).body(mensagensDto);
            }
        } else {
            mensagensDto.setMensagem("Usuário não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagensDto);
        }
        mensagensDto.setMensagem("Usuário não encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagensDto);
    }

    @Override
    public UsuarioDto converterUsuarioParaUsuarioDto(Usuario usuario) {
        return new UsuarioDto(usuario.getNomeDoUsuario());
    }
}
