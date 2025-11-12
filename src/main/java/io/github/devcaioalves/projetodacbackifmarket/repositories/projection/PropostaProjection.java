package io.github.devcaioalves.projetodacbackifmarket.repositories.projection;

import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusProposta;

import java.time.LocalDateTime;

public interface PropostaProjection {

    Long getIdProposta();
    Item getItemOfertante();
    Item getItemDestino();
    Usuario getUsuarioOfertante();
    Usuario getUsuarioDestino();
    Double getValorDiferenca();
    StatusProposta getStatus();
    LocalDateTime getDataProposta();

}
