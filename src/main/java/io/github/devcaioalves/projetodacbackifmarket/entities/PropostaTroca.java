package io.github.devcaioalves.projetodacbackifmarket.entities;

import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusProposta;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropostaTroca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProposta;

    // item ofertado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item_ofertante", nullable = false)
    private Item itemOfertante;

    // item destino
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item_destino", nullable = false)
    private Item itemDestino;

    // autor da proposta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_ofertante", nullable = false)
    private Usuario usuarioOfertante;

    // dono do item destino
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_destino", nullable = false)
    private Usuario usuarioDestino;

    private Double valorDiferenca;

    @Enumerated(EnumType.STRING)
    private StatusProposta status;

    private LocalDateTime dataProposta = LocalDateTime.now();
}