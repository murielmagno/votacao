package br.com.votacao.models;

import br.com.votacao.enums.StatusAssociado;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_ASSOCIADO")
public class Associado {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CPF
    @Column(nullable = false, length = 20)
    private String cpf;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusAssociado statusAssociado;
}
