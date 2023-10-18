package br.com.votacao.utils;

import br.com.votacao.dtos.RelatorioVotosDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class GeradorPDF {

    public static byte[] gerarPDF(List<RelatorioVotosDto> dadosDaConsulta, String pauta) throws IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (baos) {
            PdfWriter.getInstance(document, baos);
            document.open();

            Paragraph cabecalho = new Paragraph("Relatório de Votação - " + pauta);
            cabecalho.setAlignment(Element.ALIGN_CENTER);
            document.add(cabecalho);

            for (RelatorioVotosDto dado : dadosDaConsulta) {
                document.add(new Paragraph(String.valueOf(dado)));
            }

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

}
