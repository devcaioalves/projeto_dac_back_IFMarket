package io.github.devcaioalves.projetodacbackifmarket.entities;

import io.github.devcaioalves.projetodacbackifmarket.entities.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    private String matricula;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itens;

    @OneToMany(mappedBy = "usuarioDestino", cascade = CascadeType.ALL)
    private List<Notificacao> notificacoes;

}