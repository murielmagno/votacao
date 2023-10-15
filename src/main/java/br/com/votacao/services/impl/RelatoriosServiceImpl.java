package br.com.votacao.services.impl;

import br.com.votacao.dtos.RelatorioVotosDto;
import br.com.votacao.models.Votacao;
import br.com.votacao.repositories.VotacaoRepository;
import br.com.votacao.services.RelatoriosService;
import br.com.votacao.utils.GeradorPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatoriosServiceImpl implements RelatoriosService {

    @Autowired
    VotacaoRepository votacaoRepository;

    public List<RelatorioVotosDto> buscarDados(Integer id) {
        List<Object[]> result = votacaoRepository.findAllById(id);
        if (!result.isEmpty()) {
            return result.stream().map(row -> {
                var campo = 0;
                RelatorioVotosDto retorno = new RelatorioVotosDto();
                retorno.setCpf((String) row[campo++]);
                retorno.setNome((String) row[campo++]);
                return retorno;
            }).toList();
        } else {
            return new ArrayList<>();
        }
    }

    public byte[] gerarRelatorioDeVotos(Integer id) throws IOException {
        List<RelatorioVotosDto> retorno = buscarDados(id);
        Votacao votacao = votacaoRepository.findById(id);
        var pauta = "";
        if (votacao.getPauta() != null){
            pauta = votacao.getPauta().getDescricao();
        } else {
            pauta = "Não encontrado";
        }
        return GeradorPDF.gerarPDF(retorno, pauta);
    }
}
