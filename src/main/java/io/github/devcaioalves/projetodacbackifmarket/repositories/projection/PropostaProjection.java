package io.github.devcaioalves.projetodacbackifmarket.repositories.projection;

import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusProposta;

import java.time.LocalDateTime;

public interface PropostaProjection {

    Long getIdProposta();
    Double getValorDiferenca();
    StatusProposta getStatus();
    LocalDateTime getDataProposta();
    ItemResumoProjection getItemOfertante();
    ItemResumoProjection getItemDestino();
    UsuarioResumoProjection getUsuarioOfertante();
    UsuarioResumoProjection getUsuarioDestino();

}
