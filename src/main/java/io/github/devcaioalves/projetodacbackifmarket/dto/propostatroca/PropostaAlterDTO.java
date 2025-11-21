package io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca;

import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusProposta;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PropostaAlterDTO {

    private Long novoItemOfertante;
    private Long novoItemDestino;
    private Long novoUsuarioDestino;
    private Double novoValorDiferenca;
    private StatusProposta novoStatus;

}
