package io.github.devcaioalves.projetodacbackifmarket.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.NotificacaoProjection;
import io.github.devcaioalves.projetodacbackifmarket.services.NotificacaoService;
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
@RequestMapping("/br/com/ifmarket/notificacaomanagent/v1")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;
    private final ObjectMapper objectMapper;

    @PostMapping("/criar-notificacao")
    public ResponseEntity<NotificacaoResponseDTO> criarNotificacao(@RequestBody @Valid NotificacaoCreateDTO notificacaoCreateDTO) {
        NotificacaoResponseDTO notificacao = notificacaoService.criarNotificacao(notificacaoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacao);
    }

    @GetMapping("/buscar-notificacao/{id}")
    public ResponseEntity<NotificacaoResponseDTO> buscarNotificacao(@PathVariable Long id) {
        NotificacaoResponseDTO notificacao = notificacaoService.buscarNotificacao(id);
        return ResponseEntity.status(HttpStatus.OK).body(notificacao);
    }

    @GetMapping("/buscar-todas-notificacoes")
    public ResponseEntity<Page<NotificacaoResponseDTO>> buscarTodasNotificacoes(@Parameter(hidden = true) Pageable pageable) {
        Page<NotificacaoProjection> notificacoes = notificacaoService.listarTodasAsNotificacoes(pageable);
        return ResponseEntity.ok(convertToDTOPage(notificacoes, pageable));
    }

    @PutMapping("/atualizar-notificacao/{id}")
    public ResponseEntity<NotificacaoResponseDTO> atualizarNotificacao(@PathVariable Long id, @RequestBody @Valid NotificacaoAlterDTO dto) {
        NotificacaoResponseDTO notificacao = notificacaoService.atualizarNotificacao(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(notificacao);
    }

    @DeleteMapping("/deleta-notificacao/{id}")
    public ResponseEntity<NotificacaoResponseDTO> deletarNotificacao(@PathVariable Long id) {
        notificacaoService.deletarNotificacao(id);
        return ResponseEntity.noContent().build();
    }

    private Page<NotificacaoResponseDTO> convertToDTOPage(Page<NotificacaoProjection> notificacoes, Pageable pageable) {
        List<NotificacaoResponseDTO> content = notificacoes.getContent().stream()
                .map(post -> objectMapper.convertValue(post, NotificacaoResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, notificacoes.getTotalElements());
    }
}
