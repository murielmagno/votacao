package br.com.dbc.votacao.services;

import org.springframework.http.ResponseEntity;

public interface AutenticacaoService {

    ResponseEntity<Object> login(String email, String senha);
}
