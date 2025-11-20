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
public class addNotificacaoAlterDTO {

    @NotBlank(message = "O titulo é obrigatório e não pode ser vazio.")
    private String novoTitulo;
    @NotBlank(message = "A mensagem é obrigatória e não pode ser vazia.")
    private String novaMensagem;
    private Boolean novaLida = false;
    private LocalDateTime novaDataEnvio = LocalDateTime.now();
    @NotBlank(message = "o nome do usuario destino é obrigatório e não pode ser vazio.")
    private Usuario novoUsuarioDestino;
}
