package br.com.votacao.repositories;

import br.com.votacao.models.VotacaoLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacaoLogRepository extends JpaRepository<VotacaoLog, Long> {
}
