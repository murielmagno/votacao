package br.com.dbc.votacao.controllers;

import br.com.dbc.votacao.dtos.UsuarioDto;
import br.com.dbc.votacao.dtos.VotacaoDto;
import br.com.dbc.votacao.services.VotacaoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/votacao")
public class VotacaoController {

    @Autowired
    private VotacaoService votacaoService;

    public ResponseEntity<Object> iniciarVotacao(@RequestBody @Validated(VotacaoDto.VotacaoView.RegistroVotacao.class)
                                                 @JsonView(VotacaoDto.VotacaoView.RegistroVotacao.class) VotacaoDto votacaoDto) {
        return votacaoService.iniciarVotacao(votacaoDto);
    }
}
