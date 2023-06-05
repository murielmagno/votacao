package br.com.dbc.votacao.services;

import br.com.dbc.votacao.models.VotacaoLog;
import org.springframework.transaction.annotation.Transactional;

public interface VotacaoLogService {

    @Transactional
    void salvarLog(VotacaoLog log);
}
