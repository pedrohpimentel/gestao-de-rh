package com.pedro.gestao_de_rh.hrms.model;

import com.pedro.gestao_de_rh.hrms.enums.StatusFerias;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacao_ferias")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoFerias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relação Muitos-para-Um: Muitas solicitações para um único funcionário.
    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dateFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusFerias status;

}
