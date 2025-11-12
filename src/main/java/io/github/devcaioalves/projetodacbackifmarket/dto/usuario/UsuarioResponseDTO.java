package io.github.devcaioalves.projetodacbackifmarket.dto.usuario;

import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import io.github.devcaioalves.projetodacbackifmarket.entities.Notificacao;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.Role;
import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsuarioResponseDTO {

    private Long idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Role role;
    private String matricula;
    private List<Item> itens;
    private List<Notificacao> notificacoes;
}
