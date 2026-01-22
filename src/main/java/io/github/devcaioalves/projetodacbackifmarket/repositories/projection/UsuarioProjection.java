package io.github.devcaioalves.projetodacbackifmarket.repositories.projection;

import io.github.devcaioalves.projetodacbackifmarket.entities.enums.Role;

public interface UsuarioProjection {

    Long getIdUsuario();
    String getNome();
    String getEmail();
    String getTelefone();
    Role getRole();
    String getMatricula();

}
