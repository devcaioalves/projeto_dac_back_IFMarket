package io.github.devcaioalves.projetodacbackifmarket.dto.usuario;

import io.github.devcaioalves.projetodacbackifmarket.entities.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {

    @NotBlank(message = "O nome é obrigatório e não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório e não pode ser vazio.")
    private String email;

    @NotBlank(message = "A senha é obrigatória e não pode ser vazia.")
    private String senha;

    @NotBlank(message = "A confirmação da senha é obrigatória.")
    private String confirmarSenha;

    @NotBlank(message = "O telefone é obrigatório e não pode ser vazio.")
    private String telefone;

    @NotNull(message = "A função é obrigatória e não pode ser vazia.")
    private Role role;

    private String matricula;
}