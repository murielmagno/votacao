package br.com.votacao.repositories;

import br.com.votacao.models.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    Optional<Associado> findAssociadoByCpf(String cpf);

    @Query(value = "SELECT * FROM TB_VOTACAO_LISTA_DE_VOTANTES tv WHERE tv.lista_de_votantes_id = :id", nativeQuery = true)
    List<Object> findVotosToDelete(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM TB_VOTACAO_LISTA_DE_VOTANTES tv WHERE tv.lista_de_votantes_id = :id", nativeQuery = true)
    void deleteVoto(@Param("id") Long id);
}
