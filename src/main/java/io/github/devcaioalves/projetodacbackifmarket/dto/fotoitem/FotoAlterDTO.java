package io.github.devcaioalves.projetodacbackifmarket.dto.fotoitem;

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
public class FotoAlterDTO {

    @NotBlank(message = "O caminho do arquivo não pode ser vazio.")
    private String novoCaminhoArquivo;
    private Item novoItem;
}
