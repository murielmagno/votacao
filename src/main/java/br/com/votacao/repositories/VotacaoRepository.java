package br.com.votacao.repositories;

import br.com.votacao.enums.StatusVotacao;
import br.com.votacao.models.Pauta;
import br.com.votacao.models.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VotacaoRepository extends JpaRepository<Votacao, Long> {

    List<Votacao> findAllByStatusVotacao(StatusVotacao status);

    Optional<Votacao> findVotacaoByPauta(Pauta pauta);

    @Query(value = " Select ta.cpf AS cpf , tu.nome_do_usuario AS nome from TB_VOTACAO tv " +
            "inner join TB_PAUTA tp ON tv.pauta_id = tp.id " +
            "inner join TB_VOTACAO_LISTA_DE_VOTANTES listTv ON tv.id = listTv.votacao_id " +
            "inner join TB_ASSOCIADO ta ON ta.id = listTv.lista_de_votantes_id " +
            "inner join TB_USUARIO tu ON tu.cpf = ta.cpf " +
            "where tv.id = :id ", nativeQuery = true)
    List<Object[]> findAllById(@Param("id") Integer id);

    Votacao findById(@Param("id") Integer id);
}
