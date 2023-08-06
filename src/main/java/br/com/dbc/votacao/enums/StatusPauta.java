package br.com.dbc.votacao.enums;

public enum StatusPauta {

    APROVADA(0,"Pauta Aprovada"),
    CANCELADA(1,"Pauta cancelada"),
    EM_VOTACAO(2,"Pauta em votação"),
    ABERTA(3, "Pauta aberta"),
    REPROVADA(4,"Pauta Reprovada"),
    ENCERRADA (5, "Pauta encerrada sem votos");

    private int codigo;
    private String descricao;

    StatusPauta(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
