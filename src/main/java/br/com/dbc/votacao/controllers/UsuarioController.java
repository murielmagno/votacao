package br.com.dbc.votacao.controllers;

import br.com.dbc.votacao.dtos.UsuarioDto;
import br.com.dbc.votacao.services.UsuarioService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> registrarUsuario(@RequestBody @Validated(UsuarioDto.UsuarioView.RegistroUsuario.class)
                                                   @JsonView(UsuarioDto.UsuarioView.RegistroUsuario.class) UsuarioDto usuarioDto) {
        return usuarioService.salvarUsuario(usuarioDto);
    }

    @PutMapping("/alterarSenha/{nomeDoUsuario}")
    public ResponseEntity<Object> alterarSenha(@PathVariable(value = "nomeDoUsuario") String nomeDoUsuario,
                                               @RequestBody @Validated(UsuarioDto.UsuarioView.AtualizacaoSenha.class)
                                               @JsonView(UsuarioDto.UsuarioView.AtualizacaoSenha.class) UsuarioDto usuarioDto) {
        return usuarioService.alterarSenha(nomeDoUsuario, usuarioDto);
    }

    @DeleteMapping("/{nomeDoUsuario}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable(value = "nomeDoUsuario") String nomeDoUsuario) {
        return  usuarioService.deletarUsuario(nomeDoUsuario);
    }

}
