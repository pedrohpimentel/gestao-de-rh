package com.pedro.gestao_de_rh.hrms.dto.funcionario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

/*
 * DTO usado para receber dados na marcação de Registro de Ponto (POST).
 * Requer o ID do funcionário e as informações de tempo.
 */
@Data
public class RegistroDePontoRequestDTO {

    @NotNull(message = "O ID do funcionário é obrigatório.")
    private Long funcionarioId;

    @NotNull(message = "A data do ponto é obrigatória.")
    @PastOrPresent(message = "A data do ponto não pode ser futura.")
    private LocalDate data;

    @NotNull(message = "O horário de entrada é obrigatório.")
    private LocalTime entrada;

    // Saída pode ser nula se o ponto ainda não foi fechado, mas verificamos se está no presente.
    @PastOrPresent(message = "O horário de saída não pode ser no futuro.")
    private LocalTime saida;
}
