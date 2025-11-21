package io.github.devcaioalves.projetodacbackifmarket.dto.notificacao;

import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoCreateDTO {

    @NotBlank(message = "O titulo é obrigatório e não pode ser vazio.")
    private String titulo;
    @NotBlank(message = "A mensagem é obrigatória e não pode ser vazia.")
    private String mensagem;
    private Boolean lida = false;
    private LocalDateTime dataEnvio = LocalDateTime.now();
    @NotBlank(message = "o do usuario destino é obrigatório e não pode ser vazio.")
    private Usuario usuarioDestino;
}
