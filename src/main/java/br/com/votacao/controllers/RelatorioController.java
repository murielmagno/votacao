package br.com.votacao.controllers;

import br.com.votacao.services.RelatoriosService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/gerar-pdf")
public class RelatorioController {

    @Autowired
    RelatoriosService relatoriosService;

    @GetMapping("/votacao/{id}")
    public ResponseEntity<byte[]> gerarPDF(@PathVariable(value = "id") Integer id) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=Relatorio_Votacoes.pdf");

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(relatoriosService.gerarRelatorioDeVotos(id));
    }
}
