package br.com.votacao.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
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
    @NotBlank(groups = AssociadoDto.AssociadoView.RegistroAssociado.class)
    @JsonView(AssociadoDto.AssociadoView.RegistroAssociado.class)
    @CPF
    private String cpf;

}
