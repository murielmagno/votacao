package br.com.dbc.votacao.repositories;

import br.com.dbc.votacao.enums.StatusVotacao;
import br.com.dbc.votacao.models.Pauta;
import br.com.dbc.votacao.models.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VotacaoRepository extends JpaRepository<Votacao, Long> {

    List<Votacao> findAllByStatusVotacao(StatusVotacao status);

    Optional<Votacao> findVotacaoByPauta(Pauta pauta);
}
