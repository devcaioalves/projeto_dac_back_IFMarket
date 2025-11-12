package io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca;

import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusProposta;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PropostaResponseDTO {

    private Long idProposta;
    private Item itemOfertante;
    private Item itemDestino;
    private Usuario usuarioOfertante;
    private Usuario usuarioDestino;
    private Double valorDiferenca;
    private StatusProposta status = StatusProposta.PENDENTE;
    private LocalDateTime dataProposta = LocalDateTime.now();

}
