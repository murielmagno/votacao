package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.dtos.PautaDto;
import br.com.dbc.votacao.enums.StatusPauta;
import br.com.dbc.votacao.models.Pauta;
import br.com.dbc.votacao.repositories.PautaRepository;
import br.com.dbc.votacao.services.PautaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class PautaServiceImpl implements PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    @Override
    public ResponseEntity<Object> cadastrarPauta(PautaDto pautaDto) {
        try {
            var pauta = new Pauta();
            BeanUtils.copyProperties(pautaDto, pauta);
            pauta.setStatusPauta(StatusPauta.ABERTA);
            pauta.setDescricao(pautaDto.getDescricao());
            pauta.setDataCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            pauta.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            pautaRepository.save(pauta);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pauta criada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deletarPauta(Long id) {
        try {
            pautaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pauta deletada");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }

    @Override
    public Optional<Pauta> buscarPautaPorId(Long id) {
        return pautaRepository.findById(id);
    }

    @Override
    public Page<Pauta> buscarTodasAsPautas(Specification<Pauta> spec, Pageable pageable) {
        return pautaRepository.findAll(spec,pageable);
    }


}
