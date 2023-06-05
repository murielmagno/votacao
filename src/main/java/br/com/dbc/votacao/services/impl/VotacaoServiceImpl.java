package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.dtos.VotacaoDto;
import br.com.dbc.votacao.enums.StatusVotacao;
import br.com.dbc.votacao.models.Pauta;
import br.com.dbc.votacao.models.Votacao;
import br.com.dbc.votacao.repositories.VotacaoRepository;
import br.com.dbc.votacao.services.PautaService;
import br.com.dbc.votacao.services.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class VotacaoServiceImpl implements VotacaoService {

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private PautaService pautaService;

    @Override
    public ResponseEntity<Object> iniciarVotacao(VotacaoDto votacaoDto) {
        Optional<Pauta> pauta = pautaService.buscarPautaPorId(votacaoDto.getIdPauta());
        if (pauta.isPresent()) {
            var votacao = new Votacao();
            votacao.setPauta(pauta.get());
            votacao.setInicioVotacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            votacao.setStatusVotacao(StatusVotacao.ABERTA);
            if(votacaoDto.getFimVotacao() != null && !votacaoDto.getFimVotacao().equals("")){
                votacao.setFimVotacao(votacaoDto.getFimVotacao());
            } else {
                votacao.setFimVotacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusMinutes(1));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Votação Iniciada!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pauta não encontrada para votação");
        }
    }
}
