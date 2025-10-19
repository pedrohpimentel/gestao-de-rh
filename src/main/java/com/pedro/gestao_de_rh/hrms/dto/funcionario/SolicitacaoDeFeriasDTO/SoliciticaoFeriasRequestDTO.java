package com.pedro.gestao_de_rh.hrms.dto.funcionario.SolicitacaoDeFeriasDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/*
 * DTO para receber os dados de uma nova solicitação de férias.
 */
@Data
public class SoliciticaoFeriasRequestDTO {

    @NotNull(message = "O ID do funcionário é obrigatório.")
    private Long funcionarioId;

    @NotNull(message = "A data de início das férias é obrigatória.")
    @FutureOrPresent(message = "A data de início deve ser hoje ou no futuro.")
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim das férias é obrigatória.")
    @Future(message = "A data de fim deve ser futura.")
    private LocalDate dataFim;
}
