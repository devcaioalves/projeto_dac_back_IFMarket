package io.github.devcaioalves.projetodacbackifmarket.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    private String telefone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String matricula;

    // Um usuário pode ter vários itens
    @JsonIgnore
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Item> itens = new ArrayList<>();

    // Um usuário pode receber várias notificações
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioDestino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacao> notificacoes = new ArrayList<>();

    // Propostas enviadas
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioOfertante")
    private List<PropostaTroca> propostasEnviadas = new ArrayList<>();

    // Propostas recebidas
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioDestino")
    private List<PropostaTroca> propostasRecebidas = new ArrayList<>();
}