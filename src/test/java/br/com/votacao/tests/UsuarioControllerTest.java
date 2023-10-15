package br.com.votacao.tests;

import br.com.votacao.controllers.UsuarioController;
import br.com.votacao.dtos.UsuarioDto;
import br.com.votacao.services.UsuarioService;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static br.com.votacao.utils.ToJson.asJsonString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @DisplayName("Criar Usuário")
    @Test
    public void test1() throws Exception {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNomeDoUsuario("teste");
        usuarioDto.setSenha("teste123");
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .post("/usuario/cadastrar").contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(usuarioDto))).andExpect(status().isOk()).andReturn().getResponse();
        Assert.assertEquals(200,response.getStatus());

    }

    @DisplayName("Altera senha do usuário")
    @Test
    public void test2() throws Exception {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setSenhaAntiga("teste123");
        usuarioDto.setSenha("teste321");
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .put("/usuario/alterarSenha/teste").contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(usuarioDto))).andExpect(status().isOk()).andReturn().getResponse();
        Assert.assertEquals(200,response.getStatus());
    }

    @DisplayName("Deletar Usuário")
    @Test
    public void test3() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/usuario/teste").contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn().getResponse();
        Assert.assertEquals(200,response.getStatus());
    }
}
