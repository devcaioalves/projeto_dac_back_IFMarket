package io.github.devcaioalves.projetodacbackifmarket.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ItemCreateDTO {

    @NotBlank(message = "O titulo é obrigatório e não pode ser vazio.")
    private String titulo;
    @NotBlank(message = "A descrição é obrigatória e não pode ser vazia.")
    private String descricao;
    @NotNull(message = "O valor é obrigatório e não pode ser vazio.")
    private Double valor;
    private Boolean disponivelTroca;
    private Long usuarioId;
    private Long categoriaId;
}
