package com.pedro.gestao_de_rh.hrms.dto.funcionario.RegistroDePontoDTO;

import com.pedro.gestao_de_rh.hrms.dto.funcionario.funcionarioDTO.FuncionarioResponseDTO;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO usado para formatar a resposta (GET) de um Registro de Ponto.
 * Inclui o ID e o DTO de Resposta do Funcionário.
 */
@Data
@Builder
public class RegistroDePontoResponseDTO {
    private Long id;
    private Long funcionarioId;
    private String nomeFuncionario;
    private LocalDate data;
    private LocalTime entrada;
    private LocalTime saida;
    // Adiciona o funcionário para clareza na resposta.
    private FuncionarioResponseDTO funcionario;
}
