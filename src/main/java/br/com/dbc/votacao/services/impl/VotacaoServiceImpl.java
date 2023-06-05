package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.dtos.VotacaoDto;
import br.com.dbc.votacao.dtos.VotoDto;
import br.com.dbc.votacao.enums.StatusAssociado;
import br.com.dbc.votacao.enums.StatusVotacao;
import br.com.dbc.votacao.models.Associado;
import br.com.dbc.votacao.models.Pauta;
import br.com.dbc.votacao.models.Votacao;
import br.com.dbc.votacao.models.VotacaoLog;
import br.com.dbc.votacao.repositories.VotacaoRepository;
import br.com.dbc.votacao.services.AssociadoService;
import br.com.dbc.votacao.services.PautaService;
import br.com.dbc.votacao.services.VotacaoLogService;
import br.com.dbc.votacao.services.VotacaoService;
import br.com.dbc.votacao.utils.CpfValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VotacaoServiceImpl implements VotacaoService {

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private VotacaoLogService votacaoLogService;

    @Autowired
    private AssociadoService associadoService;

    @Override
    public ResponseEntity<Object> iniciarVotacao(VotacaoDto votacaoDto) {

        Optional<Pauta> pauta = pautaService.buscarPautaPorId(Long.parseLong(votacaoDto.getIdPauta()));
        LocalDateTime dateTimeNow = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        if (pauta.isPresent()) {
            var votacaoExistente = votacaoRepository.findVotacaoByPauta(pauta.get());
            if (!votacaoExistente.isPresent()) {
                var votacao = new Votacao();
                votacao.setPauta(pauta.get());
                votacao.setInicioVotacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
                votacao.setStatusVotacao(StatusVotacao.ABERTA);
                votacao.setVotosFavoraveis(0);
                votacao.setVotosContra(0);
                votacao.setTotalDeVotos(0);
                if ((votacaoDto.getFimVotacao() != null) && !votacaoDto.getFimVotacao().equals("")
                        && votacaoDto.getFimVotacao().isAfter(dateTimeNow)) {
                    votacao.setFimVotacao(votacaoDto.getFimVotacao());
                } else {
                    votacao.setFimVotacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusMinutes(1));
                }
                votacaoRepository.save(votacao);
                var log = new VotacaoLog();
                log.setDescricao("Pauta " + pauta.get().getDescricao() + " teve sua votação iniciada!");
                log.setDataCriacao(dateTimeNow);
                votacaoLogService.salvarLog(log);
                return ResponseEntity.status(HttpStatus.CREATED).body("Votação Iniciada!");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Votação para essa pauta já aconteceu!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pauta não encontrada para votação");
        }
    }

    @Override
    @Scheduled(fixedDelay = 60000)
    public void encerrarVotacaoAutomatica() {
        var log = new VotacaoLog();
        List<Votacao> votacoes = votacaoRepository.findAllByStatusVotacao(StatusVotacao.ABERTA);
        if (!votacoes.isEmpty() && votacoes != null) {
            for (Votacao votacao : votacoes) {
                LocalDateTime dateTimeNow = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
                if (votacao.getFimVotacao().isAfter(dateTimeNow)) {
                    votacao.setStatusVotacao(StatusVotacao.ENCERRADA);
                    votacao.setTotalDeVotos(votacao.getVotosFavoraveis() + votacao.getVotosContra());
                    votacaoRepository.save(votacao);
                    log.setDataCriacao(dateTimeNow);
                    log.setDescricao("Pauta " + votacao.getPauta().getDescricao() + " teve sua votação encerrada!");
                    votacaoLogService.salvarLog(log);
                }
            }
        }
    }

    @Override
    public ResponseEntity<Object> votar(String cpf, VotoDto voto) {
        var log = new VotacaoLog();
        var cpfValidator = new CpfValidator();
        if (cpfValidator.isValid(cpf)) {
            Optional<Associado> associado = associadoService.buscarAssociadoPeloCpf(cpf);
            if (associado.isPresent()) {
                if (associado.get().getStatusAssociado() == StatusAssociado.ATIVO) {
                    Optional<Votacao> votacao = votacaoRepository.findById(voto.getVotacao());
                    if (votacao.isPresent() && votacao.get().getStatusVotacao().equals(StatusVotacao.ABERTA)
                            && !votacao.get().getListaDeVotantes().contains(associado.get())) {
                        var associadosQueVotaram = new ArrayList<Associado>();
                        if (voto.getVoto().equalsIgnoreCase("sim")) {
                            LocalDateTime dateTimeNow = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
                            associadosQueVotaram.add(associado.get());
                            votacao.get().setListaDeVotantes(associadosQueVotaram);
                            votacao.get().setVotosFavoraveis(votacao.get().getVotosFavoraveis() + 1);
                            log.setDataCriacao(dateTimeNow);
                            log.setDescricao("Voto do associado " + associado.get().getCpf() + " computado com sucesso!");
                            votacaoLogService.salvarLog(log);
                            votacaoRepository.saveAndFlush(votacao.get());
                            return ResponseEntity.status(HttpStatus.OK).body("Voto realizado com sucesso.");
                        } else if (voto.getVoto().equalsIgnoreCase("não")) {
                            LocalDateTime dateTimeNow = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
                            associadosQueVotaram.add(associado.get());
                            votacao.get().setListaDeVotantes(associadosQueVotaram);
                            votacao.get().setVotosContra(votacao.get().getVotosContra() + 1);
                            log.setDataCriacao(dateTimeNow);
                            log.setDescricao("Voto do associado " + associado.get().getCpf() + " computado com sucesso!");
                            votacaoLogService.salvarLog(log);
                            votacaoRepository.saveAndFlush(votacao.get());
                            return ResponseEntity.status(HttpStatus.OK).body("Voto realizado com sucesso.");
                        } else {
                            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Voto invalído, utilize SIM ou NÃO para votar.");
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Votação encerrado ou não encontrada.");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Associado inapto para votar");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associado não encontrado para votar");
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Inapto a votar");
        }
    }
}
