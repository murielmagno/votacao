package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.dtos.UsuarioDto;
import br.com.dbc.votacao.models.Usuario;
import br.com.dbc.votacao.services.AutenticacaoService;
import br.com.dbc.votacao.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    @Autowired
    private UsuarioService usuarioService;


    @Override
    public ResponseEntity<Object> login(String nomeDoUsuario, String senha) {
        if(usuarioService.existsUsuarioByNomeDoUsuario(nomeDoUsuario)){
            Usuario usuario = usuarioService.encontrarUsuarioPeloNomeDoUsuario(nomeDoUsuario).get();
            if(usuario.getSenha().equals(senha)){
                UsuarioDto usuarioDto = usuarioService.converterUsuarioParaUsuarioDto(usuario);
                usuarioDto.setAutenticado(true);
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
