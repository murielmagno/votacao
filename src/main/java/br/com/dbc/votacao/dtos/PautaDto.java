package br.com.dbc.votacao.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    private String descricao;

}
