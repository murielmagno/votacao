package br.com.votacao.controllers;

import br.com.votacao.dtos.VotacaoDto;
import br.com.votacao.dtos.VotoDto;
import br.com.votacao.models.Votacao;
import br.com.votacao.services.VotacaoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/votacao")
public class VotacaoController {

    @Autowired
    private VotacaoService votacaoService;

    @PostMapping("/iniciar")
    public ResponseEntity<Object> iniciarVotacao(@RequestBody @Validated(VotacaoDto.VotacaoView.RegistroVotacao.class) @JsonView(VotacaoDto.VotacaoView.RegistroVotacao.class) VotacaoDto votacaoDto) {
        return votacaoService.iniciarVotacao(votacaoDto);
    }

    @PostMapping("/votar")
    public ResponseEntity<Object> votar(@RequestBody VotoDto voto) {
        return votacaoService.votar(voto);
    }

    @GetMapping("/lista")
    public ResponseEntity<Page<Votacao>> buscarTodasAsPautas(@PageableDefault(page = 0, size = 10, sort = "pauta", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Votacao> votacoes = votacaoService.buscarTodasAsVotacoes(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(votacoes);
    }

    @GetMapping("/getVotacao/{id}")
    public ResponseEntity<Votacao> buscarVotacao(@PathVariable(value = "id") Long id) {
        Votacao votacao = votacaoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(votacao);
    }
}
