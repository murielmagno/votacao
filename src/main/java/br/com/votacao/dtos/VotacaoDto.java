package br.com.votacao.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VotacaoDto {

    public interface VotacaoView {
        public static interface RegistroVotacao {
        }
    }

    @NotBlank(groups = VotacaoDto.VotacaoView.RegistroVotacao.class)
    @JsonView(VotacaoDto.VotacaoView.RegistroVotacao.class)
    private String idPauta;
    @JsonView(VotacaoDto.VotacaoView.RegistroVotacao.class)
    private LocalDateTime fimVotacao;
}
