package io.github.devcaioalves.projetodacbackifmarket.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusItem;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    private String titulo;

    @Column(length = 500)
    private String descricao;

    private Double valor;

    private String cidade;

    private Boolean disponivelTroca;

    @Enumerated(EnumType.STRING)
    private StatusItem status;

    private LocalDateTime dataAnuncio;

    // ITEM pertence a um USUARIO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // ITEM pertence a uma CATEGORIA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    // ITEM possui várias FOTOS
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FotoItem> fotos = new ArrayList<>();

    // propostas onde este item foi ofertado
    @JsonIgnore
    @OneToMany(mappedBy = "itemOfertante")
    private List<PropostaTroca> propostasOfertadas = new ArrayList<>();

    // propostas onde este item é o destino
    @JsonIgnore
    @OneToMany(mappedBy = "itemDestino")
    private List<PropostaTroca> propostasRecebidas = new ArrayList<>();
}