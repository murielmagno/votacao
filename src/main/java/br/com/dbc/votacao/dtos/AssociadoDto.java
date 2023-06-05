package br.com.dbc.votacao.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssociadoDto {

    public interface AssociadoView {
        public static interface RegistroAssociado {
        }
    }

    private Long id;
    @NotBlank
    @CPF
    private String cpf;

}
