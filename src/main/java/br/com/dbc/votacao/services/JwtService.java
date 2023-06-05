package br.com.dbc.votacao.services;

public interface JwtService {

    public String gerarToken(String username);

    public boolean validarToken(String token);

}
