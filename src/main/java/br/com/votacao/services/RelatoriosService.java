package br.com.votacao.services;

import java.io.IOException;

public interface RelatoriosService {

    public byte[] gerarRelatorioDeVotos(Integer id) throws IOException;
}
