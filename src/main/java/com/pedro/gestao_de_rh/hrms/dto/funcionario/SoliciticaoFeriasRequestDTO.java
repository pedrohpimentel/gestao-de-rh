package com.pedro.gestao_de_rh.hrms.dto.funcionario;

import lombok.Data;

import java.time.LocalDate;

/*
 * DTO para receber os dados de uma nova solicitação de férias.
 */
@Data
public class SoliciticaoFeriasRequestDTO {
    private Long funcionarioId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
