package com.pedro.gestao_de_rh.hrms.dto.funcionario;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO usado para receber dados na marcação de Registro de Ponto (POST).
 * Requer o ID do funcionário e as informações de tempo.
 */
@Data
public class RegistroDePontoRequestDTO {
    private Long funcionarioId;
    private LocalDate data;
    private LocalTime entrada;
    private LocalTime saida;
}
