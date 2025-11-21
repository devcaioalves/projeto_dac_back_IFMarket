package io.github.devcaioalves.projetodacbackifmarket.dto.notificacao;

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
    private Boolean lida;
    private LocalDateTime dataEnvio;
    private Long usuarioDestinoId;
}
