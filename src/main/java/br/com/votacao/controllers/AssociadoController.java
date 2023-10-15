package br.com.votacao.controllers;

import br.com.votacao.dtos.AssociadoDto;
import br.com.votacao.dtos.UsuarioDto;
import br.com.votacao.services.AssociadoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/associado")
public class AssociadoController {

    @Autowired
    private AssociadoService associadoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarAssociado(@RequestBody @Validated(AssociadoDto.AssociadoView.RegistroAssociado.class)
                                                 @JsonView(AssociadoDto.AssociadoView.RegistroAssociado.class) UsuarioDto usuarioDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(associadoService.salvarAssociado(usuarioDto));
    }

    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable(value = "cpf") String cpf) {
        return  associadoService.deletarAssociado(cpf);
    }
}
