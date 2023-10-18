package br.com.votacao.services;

import br.com.votacao.models.VotacaoLog;
import org.springframework.transaction.annotation.Transactional;

public interface VotacaoLogService {

    @Transactional
    void salvarLog(VotacaoLog log);
}
