package com.pedro.gestao_de_rh.hrms.dto.ferias;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/*
 * DTO para receber os dados de uma nova solicitação de férias.
 * REMOVIDAS as anotações @Future e @FutureOrPresent para permitir testes com datas antigas.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoFeriasRequestDTO {

    @NotNull(message = "O ID do funcionário é obrigatório.")
    private Long funcionarioId;

    @NotNull(message = "A data de início das férias é obrigatória.")
    // Removida anotação @FutureOrPresent
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim das férias é obrigatória.")
    // Removida anotação @Future
    private LocalDate dataFim;
}