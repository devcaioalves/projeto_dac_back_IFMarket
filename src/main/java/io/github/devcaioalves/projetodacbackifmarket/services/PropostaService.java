package io.github.devcaioalves.projetodacbackifmarket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.PropostaTroca;
import io.github.devcaioalves.projetodacbackifmarket.repositories.PropostaRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.PropostaProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final ObjectMapper objectMapper;

    public PropostaResponseDTO criarProposta(PropostaCreateDTO dto) {

        PropostaTroca proposta = objectMapper.convertValue(dto, PropostaTroca.class);

        PropostaTroca salvaProposta = propostaRepository.save(proposta);
        return convertToDTO(salvaProposta);
    }

    public PropostaResponseDTO buscarProposta(Long id) {
        PropostaTroca proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proposta de troca com id '" + id + "' não encontrada."));
        return convertToDTO(proposta);
    }

    public Page<PropostaProjection> listarTodosAsPropostas(Pageable pageable) {
        Page<PropostaProjection> page = propostaRepository.findAllBy(pageable);
        if (page.isEmpty()) {
            throw new EntityNotFoundException("Não há propostas.");
        }
        return page;
    }

    public PropostaResponseDTO atualizarProposta(Long id, PropostaAlterDTO dto) {
        PropostaTroca proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proposta de troca com id '" + id + "' não encontrada."));

        proposta.setItemOfertante(dto.getNovoItemOfertante());
        proposta.setItemDestino(dto.getNovoItemDestino());
        proposta.setUsuarioDestino(dto.getNovoUsuarioDestino());
        proposta.setValorDiferenca(dto.getNovoValorDiferenca());
        proposta.setStatus(dto.getNovoStatus());
        proposta.setDataProposta(dto.getNovaDataProposta());

        PropostaTroca propostaAtualizado = propostaRepository.save(proposta);
        return convertToDTO(propostaAtualizado);
    }

    public void deletarProposta(Long id) {
        PropostaTroca proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proposta de troca com id '" + id + "' não encontrada."));

        propostaRepository.delete(proposta);
    }

    private PropostaResponseDTO convertToDTO(PropostaTroca proposta) {
        return objectMapper.convertValue(proposta, PropostaResponseDTO.class);
    }

}
