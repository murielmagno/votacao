package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.dtos.MensagensDto;
import br.com.dbc.votacao.dtos.VotacaoDto;
import br.com.dbc.votacao.dtos.VotoDto;
import br.com.dbc.votacao.enums.StatusAssociado;
import br.com.dbc.votacao.enums.StatusPauta;
import br.com.dbc.votacao.enums.StatusVotacao;
import br.com.dbc.votacao.models.Associado;
import br.com.dbc.votacao.models.Pauta;
import br.com.dbc.votacao.models.Votacao;
import br.com.dbc.votacao.models.VotacaoLog;
import br.com.dbc.votacao.repositories.PautaRepository;
import br.com.dbc.votacao.repositories.VotacaoRepository;
import br.com.dbc.votacao.services.AssociadoService;
import br.com.dbc.votacao.services.PautaService;
import br.com.dbc.votacao.services.VotacaoLogService;
import br.com.dbc.votacao.services.VotacaoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class VotacaoServiceImpl implements VotacaoService {

    public static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";
    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private VotacaoLogService votacaoLogService;

    @Autowired
    private AssociadoService associadoService;
    @Autowired
    private PautaRepository pautaRepository;

    @Override
    public ResponseEntity<Object> iniciarVotacao(VotacaoDto votacaoDto) {
        Optional<Pauta> pauta = pautaService.buscarPautaPorId(Long.parseLong(votacaoDto.getIdPauta()));

        if (pauta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pauta não encontrada para votação");
        }

        var votacaoExistente = votacaoRepository.findVotacaoByPauta(pauta.get());
        if (votacaoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Votação para essa pauta já aconteceu!");
        }

        LocalDateTime dateTimeNow = LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO));
        return montarVotacao(votacaoDto, pauta.get(), dateTimeNow);
    }

    @Override
    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void encerrarVotacaoAutomatica() {
        var log = new VotacaoLog();
        List<Votacao> votacoes = votacaoRepository.findAllByStatusVotacao(StatusVotacao.ABERTA);
        if (!votacoes.isEmpty()) {
            for (Votacao votacao : votacoes) {
                LocalDateTime dateTimeNow = LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO));
                if (dateTimeNow.isAfter(votacao.getFimVotacao())) {
                    votacao.setStatusVotacao(StatusVotacao.ENCERRADA);
                    votacao.setTotalDeVotos(votacao.getVotosFavoraveis() + votacao.getVotosContra());
                    votacaoRepository.save(votacao);
                    log.setDataCriacao(dateTimeNow);
                    log.setDescricao("Pauta " + votacao.getPauta().getDescricao() + " teve sua votação encerrada!");
                    votacaoLogService.salvarLog(log);
                    atualizarStatusPauta(votacao);
                }
            }
        }
    }

    @Override
    public ResponseEntity<Object> votar(VotoDto voto) {
        var mensagem = new MensagensDto();
        var log = new VotacaoLog();

        Optional<Associado> associado = associadoService.buscarAssociadoPeloCpf(voto.getCpf());
        if (associado.isEmpty()) {
            mensagem.setMensagem("Associado não encontrado para votar");
            return ResponseEntity.status(HttpStatus.OK).body(mensagem);
        }

        if (associado.get().getStatusAssociado() != StatusAssociado.ATIVO) {
            mensagem.setMensagem("Associado inapto para votar");
            return ResponseEntity.status(HttpStatus.OK).body(mensagem);
        }

        Optional<Votacao> votacao = votacaoRepository.findById(voto.getVotacao());
        if (votacao.isPresent() && votacao.get().getListaDeVotantes().contains(associado.get())) {
            mensagem.setMensagem("Você já votou.");
            return ResponseEntity.status(HttpStatus.OK).body(mensagem);
        } else if (votacao.isPresent() && !votacao.get().getStatusVotacao().equals(StatusVotacao.ABERTA)) {
            mensagem.setMensagem("Votação encerrada.");
            return ResponseEntity.status(HttpStatus.OK).body(mensagem);
        }

        var associadosQueVotaram = votacao.get().getListaDeVotantes();
        if (voto.getVoto().equalsIgnoreCase("sim")) {
            return setarVotoFavoravel(log, associado.get(), votacao.get(), associadosQueVotaram);
        } else if (voto.getVoto().equalsIgnoreCase("não")) {
            return setarVotoContra(log, associado.get(), votacao.get(), associadosQueVotaram);
        } else {
            mensagem.setMensagem("Voto inválido, utilize SIM ou NÃO para votar.");
            return ResponseEntity.status(HttpStatus.OK).body(mensagem);
        }
    }

    private ResponseEntity<Object> montarVotacao(VotacaoDto votacaoDto, Pauta pauta, LocalDateTime dateTimeNow) {
        var mensagem = new MensagensDto();
        var votacao = new Votacao();
        votacao.setPauta(pauta);
        votacao.setInicioVotacao(LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
        votacao.setStatusVotacao(StatusVotacao.ABERTA);
        votacao.setVotosFavoraveis(0);
        votacao.setVotosContra(0);
        votacao.setTotalDeVotos(0);
        if (votacaoDto.getFimVotacao() != null && votacaoDto.getFimVotacao().isAfter(dateTimeNow)) {
            votacao.setFimVotacao(votacaoDto.getFimVotacao());
        } else {
            votacao.setFimVotacao(LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)).plusMinutes(1));
        }
        votacaoRepository.save(votacao);
        var log = new VotacaoLog();
        log.setDescricao("Pauta " + pauta.getDescricao() + " teve sua votação iniciada!");
        log.setDataCriacao(dateTimeNow);
        votacaoLogService.salvarLog(log);
        mensagem.setMensagem("Votação Iniciada!");
        return ResponseEntity.status(HttpStatus.OK).body(mensagem);
    }

    private ResponseEntity<Object> setarVotoFavoravel(VotacaoLog log, Associado associado, Votacao votacao, List<Associado> associadosQueVotaram) {
        var mensagem = new MensagensDto();
        LocalDateTime dateTimeNow = LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO));
        associadosQueVotaram.add(associado);
        votacao.setListaDeVotantes(associadosQueVotaram);
        votacao.setVotosFavoraveis(votacao.getVotosFavoraveis() + 1);
        votacao.setTotalDeVotos(votacao.getTotalDeVotos() + 1);
        log.setDataCriacao(dateTimeNow);
        log.setDescricao("Voto do associado " + associado.getCpf() + " computado com sucesso!");
        votacaoLogService.salvarLog(log);
        votacaoRepository.saveAndFlush(votacao);
        mensagem.setMensagem("Voto realizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(mensagem);
    }

    private ResponseEntity<Object> setarVotoContra(VotacaoLog log, Associado associado, Votacao votacao, List<Associado> associadosQueVotaram) {
        var mensagem = new MensagensDto();
        LocalDateTime dateTimeNow = LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO));
        associadosQueVotaram.add(associado);
        votacao.setListaDeVotantes(associadosQueVotaram);
        votacao.setVotosContra(votacao.getVotosContra() + 1);
        votacao.setTotalDeVotos(votacao.getTotalDeVotos() + 1);
        log.setDataCriacao(dateTimeNow);
        log.setDescricao("Voto do associado " + associado.getCpf() + " computado com sucesso!");
        votacaoLogService.salvarLog(log);
        votacaoRepository.saveAndFlush(votacao);
        mensagem.setMensagem("Voto realizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(mensagem);
    }

    private void atualizarStatusPauta(Votacao votacao) {
        Optional<Pauta> pauta = pautaService.buscarPautaPorId(votacao.getPauta().getId());
        if (pauta.isPresent()) {
            if (votacao.getTotalDeVotos() == 0) {
                pauta.get().setStatusPauta(StatusPauta.ENCERRADA);
            } else {
                int maioriaVotos = (votacao.getTotalDeVotos() / 2);
                if (votacao.getVotosFavoraveis() > maioriaVotos) {
                    pauta.get().setStatusPauta(StatusPauta.APROVADA);
                } else {
                    pauta.get().setStatusPauta(StatusPauta.REPROVADA);
                }
            }
            pautaRepository.saveAndFlush(pauta.get());
        }
    }

    @Override
    public Page<Votacao> buscarTodasAsVotacoes(Pageable pageable) {
        return votacaoRepository.findAll(pageable);
    }

    @Override
    public Votacao findById(Long id) {
        Optional<Votacao> votacao = votacaoRepository.findById(id);
        return votacao.orElseGet(Votacao::new);
    }

}
