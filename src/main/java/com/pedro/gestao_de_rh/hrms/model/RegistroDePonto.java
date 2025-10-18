package com.pedro.gestao_de_rh.hrms.model;


import com.pedro.gestao_de_rh.hrms.enums.TipoRegistro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "registro_de_ponto")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDePonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento Muitos-para-Um: Muitos registros de ponto para Um Funcionário.
    @ManyToOne(fetch = FetchType.LAZY)
    // Coluna que armazena a chave estrangeira (FK)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    // Data e hora do evento de registro
    @Column(nullable = false)
    private LocalDateTime horaRegistro;

    // Tipo de registro: ENTRADA ou SAÍDA
    @Enumerated(EnumType.STRING) // Armazena o nome do Enum (ex: "ENTRADA") no banco
    @Column(nullable = false)
    private TipoRegistro tipoRegistro;

}
