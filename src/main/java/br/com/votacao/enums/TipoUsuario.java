package br.com.votacao.enums;

public enum TipoUsuario {

    ADMIN(0,"Administrador"),
    SUPER(1,"Supervisor"),
    RELAT(2,"Relator"),
    ASSOC(3,"Associado");

    private int codigo;
    private String descricao;

    TipoUsuario(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
