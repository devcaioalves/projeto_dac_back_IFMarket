package io.github.devcaioalves.projetodacbackifmarket.dto.alterarsenha;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class EsqueciSenhaDTO {

    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;
}
