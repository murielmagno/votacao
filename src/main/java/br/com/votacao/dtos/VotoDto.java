package br.com.votacao.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VotoDto {

    private Long votacao;
    private String cpf;
    private String voto;
}
