package br.com.votacao.services.impl;

import br.com.votacao.dtos.UsuarioDto;
import br.com.votacao.models.Usuario;
import br.com.votacao.services.AutenticacaoService;
import br.com.votacao.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    @Autowired
    private UsuarioService usuarioService;


    @Override
    public ResponseEntity<Object> login(String email, String senha) {
        if(usuarioService.existsUsuarioByEmail(email)){
            Usuario usuario = usuarioService.findUsuarioByEmail(email).get();
            if(usuario.getSenha().equals(senha)){
                UsuarioDto usuarioDto = usuarioService.converterUsuarioParaUsuarioDto(usuario);
                usuarioDto.setAutenticado(true);
                usuarioDto.setCpf(usuario.getCpf());
                usuarioDto.setNomeDoUsuario(usuario.getNomeDoUsuario());
                return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);
            } else {
                UsuarioDto usuarioDto = new UsuarioDto();
                usuarioDto.setMensagem("Senha incorreta.");
                usuarioDto.setAutenticado(false);
                return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);
            }
        } else {
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setMensagem("Usuário não encontrado.");
            usuarioDto.setAutenticado(false);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);
        }
    }
}
