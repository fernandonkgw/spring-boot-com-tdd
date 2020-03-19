package com.example.demo.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "endereco")
@Getter @Setter @EqualsAndHashCode @Builder @NoArgsConstructor @AllArgsConstructor
public class Endereco {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    @EqualsAndHashCode.Exclude
    private String logradouro;

    @EqualsAndHashCode.Exclude
    private Integer numero;

    @EqualsAndHashCode.Exclude
    private String complemento;

    @EqualsAndHashCode.Exclude
    private String bairro;

    @EqualsAndHashCode.Exclude
    private String cidade;

    @EqualsAndHashCode.Exclude
    private String estado;

    @ManyToOne
    @JoinColumn(name = "codigo_pessoa")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Pessoa pessoa;
}
