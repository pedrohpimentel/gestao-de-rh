package com.pedro.gestao_de_rh.hrms.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

/*
 * Entidade para o registro diário de ponto de um funcionário.
 */
@Data
@Entity
@Table(name = "registro_ponto")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDePonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Mudança de LAZY para EAGER para evitar o erro "no session"
    // O EAGER garante que o objeto Funcionario seja carregado junto com o Ponto.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(nullable = false)
    private LocalDate data;

    private LocalTime entrada;

    private LocalTime saida;
}
