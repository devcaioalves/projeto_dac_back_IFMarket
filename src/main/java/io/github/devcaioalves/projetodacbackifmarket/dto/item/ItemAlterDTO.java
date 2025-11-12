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
public class ItemAlterDTO {

    @NotBlank(message = "O titulo é obrigatório e não pode ser vazio.")
    private String novoTitulo;
    @NotBlank(message = "A descrição é obrigatória e não pode ser vazia.")
    private String novaDescricao;
    @NotBlank(message = "O valor é obrigatório e não pode ser vazio.")
    private Double novoValor;
    private Boolean novaDisponibilidadeTroca;
    private StatusItem novoStatus = StatusItem.DISPONIVEL;
    @NotBlank(message = "O nome é obrigatório e não pode ser vazio.")
    private Categoria novaCategoria;
    private List<FotoItem> novasFotos;
}
