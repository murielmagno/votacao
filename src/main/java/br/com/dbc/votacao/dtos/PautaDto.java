package br.com.dbc.votacao.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PautaDto {

    public interface PautaView {
        public static interface RegistroPauta {
        }

        public static interface AtualizacaoPauta {
        }
    }

    private UUID id;
    @NotBlank(groups = PautaDto.PautaView.RegistroPauta.class)
    @JsonView(PautaDto.PautaView.RegistroPauta.class)
    private String descricao;

}
