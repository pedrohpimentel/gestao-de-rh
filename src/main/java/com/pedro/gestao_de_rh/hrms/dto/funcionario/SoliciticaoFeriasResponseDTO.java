package com.pedro.gestao_de_rh.hrms.dto.funcionario;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/*
 * DTO 3: Para retornar os detalhes da solicitação de férias (GET/POST/PUT).
 */
@Data
@Builder
public class SoliciticaoFeriasResponseDTO {
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status; // StatusFerias (PENDENTE, APROVADO, REJEITADO)
    private FuncionarioResponseDTO funcionario;
}
