package io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca;

import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusProposta;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PropostaAlterDTO {

    private Item novoItemOfertante;
    private Item novoItemDestino;
    private Usuario novoUsuarioDestino;
    private Double novoValorDiferenca;
    private StatusProposta novoStatus = StatusProposta.PENDENTE;
    private LocalDateTime novaDataProposta = LocalDateTime.now();

}
