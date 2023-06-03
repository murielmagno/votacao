package br.com.dbc.votacao.repositories;

import br.com.dbc.votacao.models.Associado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssociadoRepository extends JpaRepository<Associado, UUID> {
}
