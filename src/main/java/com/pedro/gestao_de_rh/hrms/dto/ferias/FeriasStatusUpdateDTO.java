package com.pedro.gestao_de_rh.hrms.dto.ferias;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO simples para receber a atualização de status de férias.
 * Recebe o status como String e o Service faz a conversão para Enum.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeriasStatusUpdateDTO {

    @NotBlank(message = "O novo status é obrigatório.")
    private String novoStatus;
}