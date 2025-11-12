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

    @ManyToOne
    @JoinColumn(name = "id_item_ofertante")
    private Item itemOfertante;

    @ManyToOne
    @JoinColumn(name = "id_item_destino")
    private Item itemDestino;

    @ManyToOne
    @JoinColumn(name = "id_usuario_ofertante")
    private Usuario usuarioOfertante;

    @ManyToOne
    @JoinColumn(name = "id_usuario_destino")
    private Usuario usuarioDestino;

    private Double valorDiferenca;

    @Enumerated(EnumType.STRING)
    private StatusProposta status = StatusProposta.PENDENTE;

    private LocalDateTime dataProposta = LocalDateTime.now();
}
