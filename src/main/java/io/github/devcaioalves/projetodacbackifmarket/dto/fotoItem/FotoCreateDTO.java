package io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FotoCreateDTO {

    private String caminhoArquivo;
    private Long itemId;
}
