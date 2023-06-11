package br.com.dbc.votacao.services;

import br.com.dbc.votacao.dtos.PautaDto;
import br.com.dbc.votacao.models.Pauta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PautaService {
    @Transactional
    ResponseEntity<Object> cadastrarPauta(PautaDto pautaDto);

    @Transactional
    ResponseEntity<Object> deletarPauta(Long id);

    Optional<Pauta> buscarPautaPorId(Long id);

    Page<Pauta> buscarTodasAsPautas(Specification<Pauta> spec, Pageable pageable);
}
