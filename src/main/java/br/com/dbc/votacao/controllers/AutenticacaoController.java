package br.com.dbc.votacao.controllers;

import br.com.dbc.votacao.dtos.UsuarioDto;
import br.com.dbc.votacao.services.AutenticacaoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UsuarioDto usuarioDto) {
        return autenticacaoService.login(usuarioDto.getEmail(), usuarioDto.getSenha());
    }

}
