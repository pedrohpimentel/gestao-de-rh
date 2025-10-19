package com.pedro.gestao_de_rh.hrms.dto.funcionario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 *Para receber o novo status da solicitação (PUT /status).
 */
@Data
public class StatusUpdateRequestDTO {

    @NotBlank(message = "O novo status (APROVADA, REJEITADA, PENDENTE) é obrigatório.")
    private String novoStatus;
}
