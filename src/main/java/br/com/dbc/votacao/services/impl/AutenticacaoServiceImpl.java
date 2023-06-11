package br.com.dbc.votacao.services.impl;

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
                return ResponseEntity.status(HttpStatus.OK).body("Usuário logado com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Senha inválida.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário não encontrado.");
        }
    }
}
