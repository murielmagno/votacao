package br.com.dbc.votacao.dtos;

import br.com.dbc.votacao.validations.UsuarioConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class UsuarioDto {

    public UsuarioDto(String nomeDoUsuario) {
    }

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

    @NotBlank(groups = UsuarioView.RegistroUsuario.class)
    @JsonView(UsuarioView.RegistroUsuario.class)
    @Size(min = 11, max = 11)
    private String cpf;

    @NotBlank(groups = {UsuarioView.AtualizacaoSenha.class, UsuarioView.RegistroUsuario.class})
    @JsonView({UsuarioView.AtualizacaoSenha.class, UsuarioView.RegistroUsuario.class})
    @Size(min = 6, max = 20, groups = {UsuarioView.AtualizacaoSenha.class, UsuarioView.RegistroUsuario.class})
    private String senha;

    @NotBlank(groups = UsuarioView.AtualizacaoSenha.class)
    @JsonView(UsuarioView.AtualizacaoSenha.class)
    @Size(min = 6, max = 20, groups = UsuarioView.AtualizacaoSenha.class)
    private String senhaAntiga;

    @NotBlank(groups = UsuarioView.RegistroUsuario.class)
    @JsonView(UsuarioView.RegistroUsuario.class)
    @Email
    private String email;

    private String mensagem;
    private boolean isAutenticado;
}
