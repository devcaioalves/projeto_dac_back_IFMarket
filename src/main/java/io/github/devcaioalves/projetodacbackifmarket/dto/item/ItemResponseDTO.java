package io.github.devcaioalves.projetodacbackifmarket.dto.item;

import io.github.devcaioalves.projetodacbackifmarket.entities.Categoria;
import io.github.devcaioalves.projetodacbackifmarket.entities.FotoItem;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusItem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ItemResponseDTO {

    private Long idItem;
    private String titulo;
    private String descricao;
    private Double valor;
    private Boolean disponivelTroca;
    private StatusItem status = StatusItem.DISPONIVEL;
    private LocalDateTime dataAnuncio = LocalDateTime.now();
    private Usuario usuario;
    private Categoria categoria;
    private List<FotoItem> fotos;

}
