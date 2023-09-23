package br.com.dbc.votacao.models;

import br.com.dbc.votacao.enums.StatusVotacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_VOTACAO")
public class Votacao {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Pauta pauta;

    @OneToMany
    private List<Associado> listaDeVotantes;

    @Column
    private Integer votosFavoraveis;

    @Column
    private Integer votosContra;

    @Column
    private Integer totalDeVotos;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime inicioVotacao;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fimVotacao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusVotacao statusVotacao;
}
