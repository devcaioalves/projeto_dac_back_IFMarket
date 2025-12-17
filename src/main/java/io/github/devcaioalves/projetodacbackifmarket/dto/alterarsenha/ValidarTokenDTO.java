package io.github.devcaioalves.projetodacbackifmarket.dto.alterarsenha;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class ValidarTokenDTO {

    @NotBlank(message = "O token é obrigatório.")
    private String token;
}
