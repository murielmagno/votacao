package br.com.dbc.votacao.dtos;

import br.com.dbc.votacao.models.Pauta;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VotacaoDto {

    public interface VotacaoView {
        public static interface RegistroVotacao {
        }
    }

    @NotBlank
    private Long idPauta;
    private LocalDateTime fimVotacao;
}
