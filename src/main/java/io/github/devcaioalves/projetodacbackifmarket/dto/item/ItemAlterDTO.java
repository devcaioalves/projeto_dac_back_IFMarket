package io.github.devcaioalves.projetodacbackifmarket.dto.item;

import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ItemAlterDTO {

    @NotBlank(message = "O titulo é obrigatório e não pode ser vazio.")
    private String novoTitulo;
    @NotBlank(message = "A descrição é obrigatória e não pode ser vazia.")
    private String novaDescricao;
    @NotNull(message = "O valor é obrigatório e não pode ser vazio.")
    private Double novoValor;
    private String novaCidade;
    private Boolean novaDisponibilidadeTroca;
    private Long novaCategoriaId;
}
