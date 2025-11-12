package io.github.devcaioalves.projetodacbackifmarket.dto.item;

import io.github.devcaioalves.projetodacbackifmarket.entities.Categoria;
import io.github.devcaioalves.projetodacbackifmarket.entities.FotoItem;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusItem;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ItemCreateDTO {

    @NotBlank(message = "O titulo é obrigatório e não pode ser vazio.")
    private String titulo;
    @NotBlank(message = "A descrição é obrigatória e não pode ser vazia.")
    private String descricao;
    @NotBlank(message = "O valor é obrigatório e não pode ser vazio.")
    private Double valor;
    private Boolean disponivelTroca;
    private StatusItem status = StatusItem.DISPONIVEL;
    private LocalDateTime dataAnuncio = LocalDateTime.now();
    private Usuario usuario;
    @NotBlank(message = "O nome é obrigatório e não pode ser vazio.")
    private Categoria categoria;
    private List<FotoItem> fotos;
}
