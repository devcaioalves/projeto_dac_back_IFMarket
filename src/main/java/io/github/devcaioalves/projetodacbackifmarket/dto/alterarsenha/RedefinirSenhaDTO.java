package io.github.devcaioalves.projetodacbackifmarket.dto.alterarsenha;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class RedefinirSenhaDTO {

    @NotBlank(message = "O token é obrigatório.")
    private String token;

    @NotBlank(message = "A senha é obrigatória.")
    private String novaSenha;
}