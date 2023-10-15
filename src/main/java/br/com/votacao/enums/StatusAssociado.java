package br.com.votacao.enums;

public enum StatusAssociado {

    ATIVO(0,"Ativo"),
    DESATIVADO(1,"Desativado");

    private int codigo;
    private String descricao;

    StatusAssociado(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
