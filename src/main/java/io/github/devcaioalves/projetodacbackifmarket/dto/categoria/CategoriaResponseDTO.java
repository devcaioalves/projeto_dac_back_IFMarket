package io.github.devcaioalves.projetodacbackifmarket.dto.categoria;

import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponseDTO {

    private Long idCategoria;
    private String nome;
    private List<Item> itens;

}
