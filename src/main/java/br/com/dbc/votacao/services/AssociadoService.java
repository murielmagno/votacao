package br.com.dbc.votacao.services;

import br.com.dbc.votacao.dtos.AssociadoDto;
import br.com.dbc.votacao.dtos.UsuarioDto;
import br.com.dbc.votacao.models.Associado;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AssociadoService {

    @Transactional(rollbackFor=Exception.class)
    ResponseEntity<Object> salvarAssociado(AssociadoDto associadoDto);

    @Transactional(rollbackFor=Exception.class)
    ResponseEntity<Object> deletarAssociado(String cpf);

    Optional<Associado> buscarAssociadoPeloCpf(String cpf);
}
