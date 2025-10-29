package io.github.devcaioalves.projetodacbackifmarket.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FotoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFoto;

    private String caminhoArquivo;

    @ManyToOne
    @JoinColumn(name = "id_item")
    private Item item;
}