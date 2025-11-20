package io.github.devcaioalves.projetodacbackifmarket.repositories.projection;

import io.github.devcaioalves.projetodacbackifmarket.entities.Item;

import java.util.List;

public interface CategoriaProjection {

    Long getIdCategoria();
    String getNome();
    List<Item> getItens();
}
