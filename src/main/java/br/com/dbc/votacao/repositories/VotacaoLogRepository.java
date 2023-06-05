package br.com.dbc.votacao.repositories;

import br.com.dbc.votacao.models.VotacaoLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacaoLogRepository extends JpaRepository<VotacaoLog, Long> {
}
