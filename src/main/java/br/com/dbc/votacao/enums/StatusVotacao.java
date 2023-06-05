package br.com.dbc.votacao.enums;

public enum StatusVotacao {

    ABERTA(0,"Votação aberta"),
    CANCELADA(1,"Votação cancelada"),
    CONCLUIDA(2,"Votação concluída");

    private int codigo;
    private String descricao;

    StatusVotacao(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
