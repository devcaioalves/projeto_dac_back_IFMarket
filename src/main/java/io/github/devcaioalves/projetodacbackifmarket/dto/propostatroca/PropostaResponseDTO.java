package io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca;

import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusProposta;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropostaResponseDTO {

    private Long idProposta;

    // IDs
    private Long itemOfertanteId;
    private Long itemDestinoId;
    private Long usuarioOfertanteId;
    private Long usuarioDestinoId;

    // Informações extras para exibir direto no frontend
    private String itemOfertanteTitulo;
    private String itemDestinoTitulo;
    private String usuarioOfertanteNome;
    private String usuarioDestinoNome;

    private Double valorDiferenca;
    private StatusProposta status;
    private LocalDateTime dataProposta;
}