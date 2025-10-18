package com.pedro.gestao_de_rh.hrms.dto.funcionario;



import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
 * DTO (Data Transfer Object) usado para receber dados na criação (POST)
 * e atualização (PUT) de um Funcionario.
 * Não inclui o ID, pois ele é gerado pelo banco.
 */
@Data
public class FuncionarioRequestDTO {
    private String nome;
    private String cargo;
    private String cpf;
    private LocalDate dataContratacao;
}
