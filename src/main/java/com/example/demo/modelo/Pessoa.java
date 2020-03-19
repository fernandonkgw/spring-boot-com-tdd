package com.example.demo.modelo;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pessoa")
@Getter @Setter @EqualsAndHashCode @Builder @NoArgsConstructor @AllArgsConstructor
public class Pessoa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    @Column(length = 80, nullable = false)
    @EqualsAndHashCode.Exclude
    private String nome;

    @Column(length = 11, nullable = false)
    @EqualsAndHashCode.Exclude
    private String cpf;

    @OneToMany(mappedBy = "pessoa")
    @EqualsAndHashCode.Exclude
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "pessoa")
    @EqualsAndHashCode.Exclude
    private List<Telefone> telefones;
}
