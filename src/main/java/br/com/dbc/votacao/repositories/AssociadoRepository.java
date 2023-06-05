package br.com.dbc.votacao.repositories;

import br.com.dbc.votacao.models.Associado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    Optional<Associado> findAssociadoByCpf(String cpf);
}
