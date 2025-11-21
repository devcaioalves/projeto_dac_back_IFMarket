package io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PropostaCreateDTO {

    private Long itemOfertante;
    private Long itemDestino;
    private Long usuarioOfertante;
    private Long usuarioDestino;
    private Double valorDiferenca;
}
