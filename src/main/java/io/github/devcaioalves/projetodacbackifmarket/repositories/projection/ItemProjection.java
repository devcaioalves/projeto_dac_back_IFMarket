package io.github.devcaioalves.projetodacbackifmarket.repositories.projection;

import io.github.devcaioalves.projetodacbackifmarket.entities.*;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusItem;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemProjection {

    Long idItem;
    String titulo;
    String descricao;
    Double valor;
    Boolean disponivelTroca;
    StatusItem status = StatusItem.DISPONIVEL;
    LocalDateTime dataAnuncio = LocalDateTime.now();
    Usuario usuario;
    Categoria categoria;
    List<FotoItem> fotos;

}
