package io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem;

import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FotoCreateDTO {

    @NotBlank(message = "O caminho do arquivo não pode ser vazio.")
    private String caminhoArquivo;
    private Item item;
}
