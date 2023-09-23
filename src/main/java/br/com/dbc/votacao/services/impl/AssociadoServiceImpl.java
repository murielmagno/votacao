package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.dtos.MensagensDto;
import br.com.dbc.votacao.dtos.UsuarioDto;
import br.com.dbc.votacao.enums.StatusAssociado;
import br.com.dbc.votacao.models.Associado;
import br.com.dbc.votacao.repositories.AssociadoRepository;
import br.com.dbc.votacao.services.AssociadoService;
import br.com.dbc.votacao.utils.CpfValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssociadoServiceImpl implements AssociadoService {

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private CpfValidator cpfValidator;

    @Override
    public ResponseEntity<Object> salvarAssociado(UsuarioDto usuarioDto) {
        try {
            var associado = new Associado();
            MensagensDto mensagensDto = new MensagensDto();
            if (cpfValidator.isValid(usuarioDto.getCpf())) {
                associado.setCpf(usuarioDto.getCpf());
                associado.setStatusAssociado(StatusAssociado.ATIVO);
                associadoRepository.save(associado);
                mensagensDto.setMensagem("Associado com CPF " + associado.getCpf() + " cadastrado com sucesso.");
                return ResponseEntity.status(HttpStatus.CREATED).body(mensagensDto);
            } else {
                mensagensDto.setMensagem("CPF inválido!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagensDto);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> deletarAssociado(String cpf) {
        try {
            Optional<Associado> associado = associadoRepository.findAssociadoByCpf(cpf);
            MensagensDto mensagensDto = new MensagensDto();
            if (associado.isPresent()) {
                if (associadoRepository.findVotosToDelete(associado.get().getId()) != null) {
                    associadoRepository.deleteVoto(associado.get().getId());
                }
                associadoRepository.deleteById(associado.get().getId());
                mensagensDto.setMensagem("Associado com CPF " + associado.get().getCpf() + " deletado.");
                return ResponseEntity.status(HttpStatus.OK).body(mensagensDto);
            } else {
                mensagensDto.setMensagem("Associado não encontrado!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagensDto);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }

    @Override
    public Optional<Associado> buscarAssociadoPeloCpf(String cpf) {
        return associadoRepository.findAssociadoByCpf(cpf);
    }


}
