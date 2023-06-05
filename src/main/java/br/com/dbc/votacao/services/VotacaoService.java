package br.com.dbc.votacao.services;

import br.com.dbc.votacao.dtos.VotacaoDto;
import br.com.dbc.votacao.dtos.VotoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface VotacaoService {

    @Transactional(rollbackFor=Exception.class)
    ResponseEntity<Object> iniciarVotacao(VotacaoDto votacaoDto);

    @Transactional(rollbackFor=Exception.class)
    void encerrarVotacaoAutomatica();

    @Transactional
    ResponseEntity<Object> votar(String cpf, VotoDto voto);
}
