package br.com.dbc.votacao.dtos;

import lombok.*;

@Data
public class RelatorioVotosDto {

    private String cpf;
    private String nome;

    @Override
    public String toString() {
        return "CPF: " + cpf + ", Nome: " + nome;
    }
}
