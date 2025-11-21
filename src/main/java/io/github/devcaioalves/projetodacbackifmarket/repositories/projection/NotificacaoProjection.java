package io.github.devcaioalves.projetodacbackifmarket.repositories.projection;

import java.time.LocalDateTime;

public interface NotificacaoProjection {

    Long getIdNotificacao();
    String getTitulo();
    String getMensagem();
    Boolean getLida();
    LocalDateTime getDataEnvio = LocalDateTime.now();
    UsuarioResumoProjection getUsuarioDestino();

}