package br.com.dbc.votacao.controllers;

import br.com.dbc.votacao.dtos.PautaDto;
import br.com.dbc.votacao.services.PautaService;
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
@RequestMapping("/pauta")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarPauta(@RequestBody @Validated(PautaDto.PautaView.RegistroPauta.class)
                                                 @JsonView(PautaDto.PautaView.RegistroPauta.class) PautaDto pautaDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaService.cadastrarPauta(pautaDto));
    }
}
