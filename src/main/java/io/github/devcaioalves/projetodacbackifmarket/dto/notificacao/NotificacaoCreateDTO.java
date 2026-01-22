package io.github.devcaioalves.projetodacbackifmarket.dto.notificacao;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoCreateDTO {

    @NotBlank(message = "O titulo é obrigatório e não pode ser vazio.")
    private String titulo;
    @NotBlank(message = "A mensagem é obrigatória e não pode ser vazia.")
    private String mensagem;
    private Long usuarioDestinoId;
}
