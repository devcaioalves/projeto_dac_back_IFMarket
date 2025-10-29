package io.github.devcaioalves.projetodacbackifmarket.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    private String titulo;

    @Column(length = 500)
    private String descricao;

    private Double valor;

    private Boolean disponivelTroca;

    @Enumerated(EnumType.STRING)
    private StatusItem status = StatusItem.DISPONIVEL;

    private LocalDateTime dataAnuncio = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FotoItem> fotos;
}