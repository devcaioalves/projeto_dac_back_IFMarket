package io.github.devcaioalves.projetodacbackifmarket.dto.usuario;

import io.github.devcaioalves.projetodacbackifmarket.entities.enums.Role;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsuarioResponseDTO {

    private Long idUsuario;
    private String nome;
    private String email;
    private String telefone;
    private Role role;
    private String matricula;
}
