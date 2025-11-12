package io.github.devcaioalves.projetodacbackifmarket.dto.usuario;

import io.github.devcaioalves.projetodacbackifmarket.entities.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsuarioAlterDTO {

    @NotBlank(message = "O nome é obrigatório e não pode ser vazio.")
    private String novoNome;
    @NotBlank(message = "O e-mail é obrigatório e não pode ser vazio.")
    private String novoEmail;
    @NotBlank(message = "A senha é obrigatória e não pode ser vazia.")
    private String novaSenha;
    @NotBlank(message = "O telefone é obrigatório e não pode ser vazio.")
    private String novoTelefone;
    @NotBlank(message = "A função é obrigatória e não pode ser vazia.")
    private Role novoRole;
}
