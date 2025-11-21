package io.github.devcaioalves.projetodacbackifmarket.repositories.projection;

import io.github.devcaioalves.projetodacbackifmarket.entities.Item;


public interface FotoProjection {

    Long getIdFoto();
    String getCaminhoArquivo();
    Item getItem();

}
