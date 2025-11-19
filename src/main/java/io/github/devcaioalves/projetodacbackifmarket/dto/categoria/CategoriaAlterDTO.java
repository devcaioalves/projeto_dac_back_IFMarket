package io.github.devcaioalves.projetodacbackifmarket.dto.categoria;

import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaAlterDTO {

    @NotBlank(message = "O nome é obrigatório e não pode ser vazio.")
    private String novoNome;
    private List<Item> novosItens;

}
