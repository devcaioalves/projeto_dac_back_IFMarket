package io.github.devcaioalves.projetodacbackifmarket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.Notificacao;
import io.github.devcaioalves.projetodacbackifmarket.repositories.NotificacaoRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.NotificacaoProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final ObjectMapper objectMapper;

    public NotificacaoResponseDTO criarNotificacao(NotificacaoCreateDTO dto) {

        Notificacao notificacao = objectMapper.convertValue(dto, Notificacao.class);

        Notificacao salvaNotificacao = notificacaoRepository.save(notificacao);
        return convertToDTO(salvaNotificacao);
    }

    public NotificacaoResponseDTO buscarNotificacao(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificacao com id '" + id + "' não encontrado."));
        return convertToDTO(notificacao);
    }

    public Page<NotificacaoProjection> listarTodasAsNotificacoes(Pageable pageable) {
        Page<NotificacaoProjection> page = notificacaoRepository.findAllBy(pageable);
        if (page.isEmpty()) {
            throw new EntityNotFoundException("Não há notificacoes.");
        }
        return page;
    }

    public NotificacaoResponseDTO atualizarNotificacao(Long id, NotificacaoAlterDTO dto) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificacao com id '" + id + "' não encontrada."));

        notificacao.setTitulo(dto.getNovoTitulo());
        notificacao.setMensagem(dto.getNovaMensagem());
        notificacao.setLida(dto.getNovaLida());
        notificacao.setDataEnvio(dto.getNovaDataEnvio());
        notificacao.setUsuarioDestino(dto.getNovoUsuarioDestino());

        Notificacao notificacaoAtualizada = notificacaoRepository.save(notificacao);
        return convertToDTO(notificacaoAtualizada);
    }

    public void deletarNotificacao(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificacao com id '" + id + "' não encontrada."));

        notificacaoRepository.delete(notificacao);
    }

    private NotificacaoResponseDTO convertToDTO(Notificacao notificacao) {
        return objectMapper.convertValue(notificacao, NotificacaoResponseDTO.class);
    }
}
