package br.com.dbc.votacao.enums;

public enum TipoUsuario {

    ADMIN(0,"Administrador"),
    SUPER(1,"Supervisor"),
    RELAT(2,"Relator");

    private int codigo;
    private String descricao;

    TipoUsuario(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
