package io.github.devcaioalves.projetodacbackifmarket.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAlterPassDTO {

    @NotBlank(message = "A senha atual é obrigatória.")
    private String senhaAtual;

    @NotBlank(message = "A nova senha é obrigatória.")
    private String novaSenha;

    @NotBlank(message = "A confirmação da nova senha é obrigatória.")
    private String confirmarNovaSenha;
}
