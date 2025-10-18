package com.pedro.gestao_de_rh.hrms.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "funcionario")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true,nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String cargo;

    @Column(name = "data_contratação",nullable = false)
    private LocalDate dataContratacao;
}
