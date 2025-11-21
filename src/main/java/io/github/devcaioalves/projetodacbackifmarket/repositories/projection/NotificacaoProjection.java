package io.github.devcaioalves.projetodacbackifmarket.repositories.projection;

import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;

import java.time.LocalDateTime;

public interface NotificacaoProjection {

    Long getIdNotificacao();
    String getTitulo();
    String getMensagem();
    Boolean getLida();
    LocalDateTime getDataEnvio = LocalDateTime.now();
    Usuario getUsuarioDestino();

}