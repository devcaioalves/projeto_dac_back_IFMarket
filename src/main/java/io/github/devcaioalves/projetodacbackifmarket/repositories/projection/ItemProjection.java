package io.github.devcaioalves.projetodacbackifmarket.repositories.projection;

import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusItem;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemProjection {

    Long getIdItem();
    String getTitulo();
    String getDescricao();
    Double getValor();
    Boolean getDisponivelTroca();
    StatusItem getStatus();
    LocalDateTime getDataAnuncio();
    UsuarioResumoProjection getUsuario();
    CategoriaProjection getCategoria();
    List<FotoProjection> getFotos();

}
