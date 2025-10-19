package com.pedro.gestao_de_rh.hrms.dto.ponto;

import lombok.Builder;
import lombok.Data;

/*
 * DTO de resposta para o cálculo do total de horas trabalhadas em um mês.
 */
@Data
@Builder
public class TotalHorasTrabalhadasDTO {
    private Long funcionarioId;
    private int mes;
    private int ano;
    private String totalHoras; // Ex: "176h 30m"
    private Long totalMinutos; // Total de minutos para facilitar outras operações
}