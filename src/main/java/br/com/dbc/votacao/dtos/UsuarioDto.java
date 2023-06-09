package br.com.dbc.votacao.dtos;

import br.com.dbc.votacao.validations.UsuarioConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto {

    public interface UsuarioView {
        public static interface RegistroUsuario {
        }

        public static interface AtualizacaoSenha {
        }
    }

    private Long id;

    @NotBlank(groups = UsuarioView.RegistroUsuario.class)
    @JsonView(UsuarioView.RegistroUsuario.class)
    @Size(min = 3, max = 10, groups = UsuarioView.RegistroUsuario.class)
    @UsuarioConstraint
    private String nomeDoUsuario;

    @NotBlank(groups = {UsuarioView.AtualizacaoSenha.class, UsuarioView.RegistroUsuario.class})
    @JsonView({UsuarioView.AtualizacaoSenha.class, UsuarioView.RegistroUsuario.class})
    @Size(min = 8, max = 20, groups = {UsuarioView.AtualizacaoSenha.class, UsuarioView.RegistroUsuario.class})
    private String senha;

    @NotBlank(groups = UsuarioView.AtualizacaoSenha.class)
    @JsonView(UsuarioView.AtualizacaoSenha.class)
    @Size(min = 8, max = 20, groups = UsuarioView.AtualizacaoSenha.class)
    private String senhaAntiga;

}
