package br.com.votacao.services.impl;

import br.com.votacao.models.VotacaoLog;
import br.com.votacao.repositories.VotacaoLogRepository;
import br.com.votacao.services.VotacaoLogService;
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
