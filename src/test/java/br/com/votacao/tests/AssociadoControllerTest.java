package br.com.votacao.tests;

import br.com.votacao.controllers.AssociadoController;
import br.com.votacao.dtos.AssociadoDto;
import br.com.votacao.services.AssociadoService;
import br.com.votacao.utils.ToJson;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AssociadoController.class)
class AssociadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssociadoService associadoService;

    @DisplayName("Criar Associado")
    @Test
    void test1() throws Exception {
        var associadoDto = new AssociadoDto();
        associadoDto.setCpf("91737015056");
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/associado/cadastrar").contentType(APPLICATION_JSON_UTF8).content(ToJson.asJsonString(associadoDto))).andExpect(status().isCreated()).andReturn().getResponse();
        Assert.assertEquals(201, response.getStatus());
    }

}
