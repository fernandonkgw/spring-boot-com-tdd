package com.example.demo.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "telefone")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Telefone {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    @Column(length = 2, nullable = false)
    @EqualsAndHashCode.Exclude
    private String ddd;

    @Column(length = 9, nullable = false)
    @EqualsAndHashCode.Exclude
    private String numero;

    @ManyToOne
    @JoinColumn(name = "codigo_pessoa")
    @JsonIgnore // evitar dependência ciclica
    @EqualsAndHashCode.Exclude
    private Pessoa pessoa;
}
