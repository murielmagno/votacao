package br.com.dbc.votacao.controllers;

import br.com.dbc.votacao.services.RelatoriosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RelatorioController {

    @Autowired
    RelatoriosService relatoriosService;

    @GetMapping("/gerar-pdf/{id}")
    public ResponseEntity<byte[]> gerarPDF(@PathVariable(value = "id") Integer id) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=Relatorio_Votacoes.pdf");

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(relatoriosService.gerarRelatorioDeVotos(id));
    }
}
