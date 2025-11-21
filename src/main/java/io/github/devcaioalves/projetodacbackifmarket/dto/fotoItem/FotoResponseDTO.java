package io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem;

import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FotoResponseDTO {

    private Long idFoto;
    private String caminhoArquivo;
    private Item item;
}
