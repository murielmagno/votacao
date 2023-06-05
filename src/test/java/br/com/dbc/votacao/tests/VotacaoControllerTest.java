package br.com.dbc.votacao.tests;

import br.com.dbc.votacao.controllers.VotacaoController;
import br.com.dbc.votacao.dtos.AssociadoDto;
import br.com.dbc.votacao.dtos.VotacaoDto;
import br.com.dbc.votacao.services.VotacaoService;
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
@WebMvcTest(VotacaoController.class)
public class VotacaoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotacaoService votacaoService;

    @DisplayName("Iniciar votação")
    @Test
    public void test1() throws Exception {
        var votacaoDto = new VotacaoDto();
        votacaoDto.setIdPauta("1");
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .post("/votacao/iniciar").contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(votacaoDto))).andExpect(status().isOk()).andReturn().getResponse();
        Assert.assertEquals(200,response.getStatus());
    }
}
