package io.github.devcaioalves.projetodacbackifmarket.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaCreateDTO {

    @NotBlank(message = "O nome é obrigatório e não pode ser vazio.")
    private String nome;

}
