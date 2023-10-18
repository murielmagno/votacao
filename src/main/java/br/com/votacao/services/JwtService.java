package br.com.votacao.services;

public interface JwtService {

    public String gerarToken(String username);

    public boolean validarToken(String token);

}
