package io.github.devcaioalves.projetodacbackifmarket.services;

import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.Notificacao;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.repositories.NotificacaoRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.UsuarioRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.NotificacaoProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;


    public NotificacaoResponseDTO criarNotificacao(NotificacaoCreateDTO dto) {

        Usuario usuarioDestino = usuarioRepository.findById(dto.getUsuarioDestinoId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário destino não encontrado."));

        Notificacao notificacao = new Notificacao();
        notificacao.setTitulo(dto.getTitulo());
        notificacao.setMensagem(dto.getMensagem());
        notificacao.setLida(false);
        notificacao.setDataEnvio(LocalDateTime.now());
        notificacao.setUsuarioDestino(usuarioDestino);

        Notificacao salva = notificacaoRepository.save(notificacao);
        return convertToDTO(salva);
    }

    public NotificacaoResponseDTO buscarNotificacao(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Notificação com id '" + id + "' não encontrada."));

        return convertToDTO(notificacao);
    }

    public Page<NotificacaoProjection> listarTodasAsNotificacoes(Pageable pageable) {
        Page<NotificacaoProjection> page = notificacaoRepository.findAllBy(pageable);

        if (page.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma notificação encontrada.");
        }

        return page;
    }

    public NotificacaoResponseDTO atualizarNotificacao(Long id, NotificacaoAlterDTO dto) {

        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Notificação com id '" + id + "' não encontrada."));

        Usuario novoUsuarioDestino = usuarioRepository.findById(dto.getNovoUsuarioDestinoId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário destino não encontrado."));

        notificacao.setTitulo(dto.getNovoTitulo());
        notificacao.setMensagem(dto.getNovaMensagem());
        notificacao.setLida(dto.getNovaConfirmacaoLeitura());
        notificacao.setUsuarioDestino(novoUsuarioDestino);

        Notificacao atualizada = notificacaoRepository.save(notificacao);
        return convertToDTO(atualizada);
    }

    public void deletarNotificacao(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Notificação com id '" + id + "' não encontrada."));

        notificacaoRepository.delete(notificacao);
    }

    private NotificacaoResponseDTO convertToDTO(Notificacao notificacao) {
        return new NotificacaoResponseDTO(
                notificacao.getIdNotificacao(),
                notificacao.getTitulo(),
                notificacao.getMensagem(),
                notificacao.getLida(),
                notificacao.getDataEnvio(),
                notificacao.getUsuarioDestino() != null ? notificacao.getUsuarioDestino().getIdUsuario() : null
        );
    }
}
