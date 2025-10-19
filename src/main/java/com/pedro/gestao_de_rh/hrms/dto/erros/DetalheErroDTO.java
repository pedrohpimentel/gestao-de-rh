package com.pedro.gestao_de_rh.hrms.dto.erros;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/*
 * DTO padronizado para representar detalhes de um erro na resposta da API.
 */
@Data
@Builder
public class DetalheErroDTO {
    private LocalDateTime timestamp;
    private int status;
    private String erro;
    private String mensagem;
    private String path;
    private List<String> detalhes; // Usado para listar erros de validação (@Valid)
}
