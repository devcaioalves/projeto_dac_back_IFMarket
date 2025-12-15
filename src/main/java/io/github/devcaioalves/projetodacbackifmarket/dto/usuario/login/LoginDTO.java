package io.github.devcaioalves.projetodacbackifmarket.dto.usuario.login;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String identificador; // email ou matricula
    private String senha;
}
