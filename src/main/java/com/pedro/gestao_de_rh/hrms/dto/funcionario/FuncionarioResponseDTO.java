package com.pedro.gestao_de_rh.hrms.dto.funcionario;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
 * DTO (Data Transfer Object) usado para formatar a resposta (GET)
 * de um Funcionario. Contém o ID.
 * Usamos @Builder para facilitar a conversão no Service.
 */
@Data
@Builder
public class FuncionarioResponseDTO {
    private Long id;
    private String nome;
    private String cargo;
    private String cpf;
    private LocalDate dataContratacao;
}
