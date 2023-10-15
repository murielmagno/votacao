package br.com.votacao.services;

import br.com.votacao.dtos.PautaDto;
import br.com.votacao.models.Pauta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PautaService {
    @Transactional
    ResponseEntity<Object> cadastrarPauta(PautaDto pautaDto);

    @Transactional
    ResponseEntity<Object> deletarPauta(Long id);

    @Transactional(readOnly = true)
    Optional<Pauta> buscarPautaPorId(Long id);

    @Transactional(readOnly = true)
    Page<Pauta> buscarTodasAsPautas(Pageable pageable);
}
