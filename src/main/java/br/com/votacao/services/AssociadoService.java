package br.com.votacao.services;

import br.com.votacao.dtos.UsuarioDto;
import br.com.votacao.models.Associado;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AssociadoService {

    @Transactional(rollbackFor=Exception.class)
    ResponseEntity<Object> salvarAssociado(UsuarioDto usuarioDto);

    @Transactional(rollbackFor=Exception.class)
    ResponseEntity<Object> deletarAssociado(String cpf);

    Optional<Associado> buscarAssociadoPeloCpf(String cpf);
}
