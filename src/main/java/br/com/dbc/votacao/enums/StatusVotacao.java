package br.com.dbc.votacao.enums;

public enum StatusVotacao {

    ABERTA(0,"Votação aberta"),
    CANCELADA(1,"Votação cancelada"),
    ENCERRADA(2,"Votação encerrada");

    private int codigo;
    private String descricao;

    StatusVotacao(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
