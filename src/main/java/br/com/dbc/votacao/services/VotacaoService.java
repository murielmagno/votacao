package br.com.dbc.votacao.services;

import br.com.dbc.votacao.dtos.VotacaoDto;
import br.com.dbc.votacao.dtos.VotoDto;
import br.com.dbc.votacao.models.Votacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VotacaoService {

    @Transactional(rollbackFor=Exception.class)
    ResponseEntity<Object> iniciarVotacao(VotacaoDto votacaoDto);

    @Transactional(rollbackFor=Exception.class)
    void encerrarVotacaoAutomatica();

    @Transactional(rollbackFor=Exception.class)
    ResponseEntity<Object> votar(String cpf, VotoDto voto);

    @Transactional(readOnly = true)
    Page<Votacao> buscarTodasAsVotacoes(Pageable pageable);

    @Transactional(readOnly = true)
    Votacao findById(Long id);
}
