package br.com.dbc.votacao.repositories;

import br.com.dbc.votacao.models.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
}
