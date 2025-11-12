package io.github.devcaioalves.projetodacbackifmarket.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.docs.PropostaDoc;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.PropostaProjection;
import io.github.devcaioalves.projetodacbackifmarket.services.PropostaService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/br/com/ifmarket/propostamanagent/v1")
public class PropostaController implements PropostaDoc {

    private final PropostaService propostaService;
    private final ObjectMapper objectMapper;

    @PostMapping("/criar-proposta")
    public ResponseEntity<PropostaResponseDTO> criarProposta(@RequestBody @Valid PropostaCreateDTO propostaCreateDTO) {
        PropostaResponseDTO proposta = propostaService.criarProposta(propostaCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(proposta);
    }

    @GetMapping("/buscar-proposta/{id}")
    public ResponseEntity<PropostaResponseDTO> buscarProposta(@PathVariable Long id) {
        PropostaResponseDTO proposta = propostaService.buscarProposta(id);
        return ResponseEntity.status(HttpStatus.OK).body(proposta);
    }

    @GetMapping("/buscar-todas-proposta")
    public ResponseEntity<Page<PropostaResponseDTO>> buscarTodasAsPropostas(@Parameter(hidden = true) Pageable pageable) {
        Page<PropostaProjection> propostas = propostaService.listarTodosAsPropostas(pageable);
        return ResponseEntity.ok(convertToDTOPage(propostas, pageable));
    }

    @PutMapping("/atualizar-proposta/{id}")
    public ResponseEntity<PropostaResponseDTO> atualizarProposta(@PathVariable Long id, @RequestBody @Valid PropostaAlterDTO dto) {
        PropostaResponseDTO proposta = propostaService.atualizarProposta(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(proposta);
    }

    @DeleteMapping("/deleta-proposta/{id}")
    public ResponseEntity<PropostaResponseDTO> deletarProposta(@PathVariable Long id) {
        propostaService.deletarProposta(id);
        return ResponseEntity.noContent().build();
    }

    private Page<PropostaResponseDTO> convertToDTOPage(Page<PropostaProjection> propostas, Pageable pageable) {
        List<PropostaResponseDTO> content = propostas.getContent().stream()
                .map(post -> objectMapper.convertValue(post, PropostaResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, propostas.getTotalElements());
    }

}
