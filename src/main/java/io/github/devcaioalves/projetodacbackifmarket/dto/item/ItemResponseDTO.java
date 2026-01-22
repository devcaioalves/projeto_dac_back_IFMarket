package io.github.devcaioalves.projetodacbackifmarket.dto.item;

import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoResponseDTO;
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
    private String cidade;
    private Boolean disponivelTroca;
    private StatusItem status;
    private LocalDateTime dataAnuncio;
    private Long usuarioId;
    private Long categoriaId;
    private List<FotoResponseDTO> fotos;

}
