package io.github.devcaioalves.projetodacbackifmarket.controllers;

import io.github.devcaioalves.projetodacbackifmarket.docs.PropostaDoc;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.services.PropostaService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/br/com/ifmarket/propostamanagent/v1")
public class PropostaController implements PropostaDoc {

    private final PropostaService propostaService;

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
        Page<PropostaResponseDTO> propostas = propostaService.listarTodasPropostas(pageable);
        return ResponseEntity.ok(propostas);
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

}
