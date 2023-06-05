package br.com.dbc.votacao.tests;

import br.com.dbc.votacao.controllers.PautaController;
import br.com.dbc.votacao.dtos.AssociadoDto;
import br.com.dbc.votacao.dtos.PautaDto;
import br.com.dbc.votacao.services.PautaService;
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

import static br.com.dbc.votacao.utils.ToJson.asJsonString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PautaController.class)
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PautaService pautaService;

    @DisplayName("Criar Pauta")
    @Test
    public void test1() throws Exception {
        var pautaDto = new PautaDto();
        pautaDto.setDescricao("Pauta para teste");
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .post("/pauta/cadastrar").contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(pautaDto))).andExpect(status().isCreated()).andReturn().getResponse();
        Assert.assertEquals(201,response.getStatus());
    }
}
