package br.com.dbc.votacao.services.impl;

import br.com.dbc.votacao.dtos.AssociadoDto;
import br.com.dbc.votacao.enums.StatusAssociado;
import br.com.dbc.votacao.models.Associado;
import br.com.dbc.votacao.repositories.AssociadoRepository;
import br.com.dbc.votacao.services.AssociadoService;
import br.com.dbc.votacao.utils.CpfValidator;
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
    public ResponseEntity<Object> salvarAssociado(AssociadoDto associadoDto) {
        try {
            var associado = new Associado();
            if(cpfValidator.isValid(associadoDto.getCpf())){
                associado.setCpf(associadoDto.getCpf());
                associado.setStatusAssociado(StatusAssociado.ATIVO);
                associadoRepository.save(associado);
                return ResponseEntity.status(HttpStatus.CREATED).body("Associado com CPF " + associado.getCpf() + " cadastrado com sucesso.");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido!");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deletarAssociado(String cpf) {
        try {
            Optional<Associado> associado = associadoRepository.findAssociadoByCpf(cpf);
            if(associado.isPresent()) {
                associadoRepository.deleteById(associado.get().getId());
                return ResponseEntity.status(HttpStatus.OK).body("Associado com CPF " + associado.get().getCpf() + " deletado.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associado não encontrado!");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }


}
