package br.com.dbc.votacao.repositories;

import br.com.dbc.votacao.models.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
}
