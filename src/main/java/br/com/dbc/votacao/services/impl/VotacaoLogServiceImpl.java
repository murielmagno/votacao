package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.models.VotacaoLog;
import br.com.dbc.votacao.repositories.VotacaoLogRepository;
import br.com.dbc.votacao.services.VotacaoLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotacaoLogServiceImpl implements VotacaoLogService {

    @Autowired
    private VotacaoLogRepository votacaoLogRepository;

    @Override
    public void salvarLog(VotacaoLog log) {
        votacaoLogRepository.save(log);
    }
}
