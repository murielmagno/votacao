package br.com.dbc.votacao.repositories;

import br.com.dbc.votacao.models.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PautaRepository extends JpaRepository<Pauta, Long>, JpaSpecificationExecutor<Pauta> {
}
