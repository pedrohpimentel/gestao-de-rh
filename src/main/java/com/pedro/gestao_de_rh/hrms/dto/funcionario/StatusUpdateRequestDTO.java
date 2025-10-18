package com.pedro.gestao_de_rh.hrms.dto.funcionario;

import lombok.Data;

/*
 *Para receber o novo status da solicitação (PUT /status).
 */
@Data
public class StatusUpdateRequestDTO {
    private String novoStatus;
}
