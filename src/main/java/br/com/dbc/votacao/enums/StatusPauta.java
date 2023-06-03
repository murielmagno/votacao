package br.com.dbc.votacao.enums;

public enum StatusPauta {

    VOTADA(0,"Pauta concluída"),
    CANCELADA(1,"Pauta cancelada"),
    EM_VOTACAO(2,"Pauta em votação"),
    ABERTA(3, "Pauta aberta");

    private int codigo;
    private String descricao;

    StatusPauta(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
