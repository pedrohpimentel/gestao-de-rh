package com.pedro.gestao_de_rh.hrms.dto.funcionario.funcionarioDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/*
 * DTO (Data Transfer Object) usado para receber dados na criação (POST)
 * e atualização (PUT) de um Funcionario.
 * Não inclui o ID, pois ele é gerado pelo banco.
 */
@Data
public class FuncionarioRequestDTO {

    @NotBlank(message = "O nome não pode ser vazio.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;


    @NotBlank(message = "O cargo não pode ser vazio.")
    @Size(min = 2, max = 50, message = "O cargo deve ter entre 2 e 50 caracteres.")
    private String cargo;

    // Embora a validação de CPF exija uma biblioteca externa, garantimos que não seja nulo e tenha 11 dígitos.
    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos.")
    private String cpf;

    @NotNull(message = "A data de contratação é obrigatória.")
    @PastOrPresent(message = "A data de contratação não pode ser no futuro.")
    private LocalDate dataContratacao;
}
