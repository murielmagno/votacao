package br.com.dbc.votacao.services;

import br.com.dbc.votacao.dtos.PautaDto;
import br.com.dbc.votacao.models.Pauta;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PautaService {
    @Transactional
    ResponseEntity<Object> cadastrarPauta(PautaDto pautaDto);

    @Transactional
    ResponseEntity<Object> deletarPauta(Long id);

    Optional<Pauta> buscarPautaPorId(Long id);
}
