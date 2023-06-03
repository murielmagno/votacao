package br.com.dbc.votacao.repositories;

import br.com.dbc.votacao.models.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PautaRepository extends JpaRepository<Pauta, UUID> {
}
