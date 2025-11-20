package io.github.devcaioalves.projetodacbackifmarket.dto.notificacao;

import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoResponseDTO {

    private Long idNotificacao;
    private String titulo;
    private String mensagem;
    private Boolean lida = false;
    private LocalDateTime dataEnvio = LocalDateTime.now();
    private Usuario usuarioDestino;
}
