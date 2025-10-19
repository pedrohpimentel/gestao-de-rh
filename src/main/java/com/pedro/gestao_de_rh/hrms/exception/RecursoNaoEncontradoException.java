package com.pedro.gestao_de_rh.hrms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Exceção customizada para ser lançada quando um recurso
 * (entidade) procurado por ID não é encontrado no banco de dados.
 * Estende RuntimeException para ser uma exceção não verificada (unchecked).
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNaoEncontradoException extends RuntimeException {
    // Construtor que aceita o nome do recurso e o ID
    public RecursoNaoEncontradoException(String recurso, Long id) {
        // Chama o construtor pai com a mensagem formatada
        super(String.format("%s com ID %d não foi encontrado.", recurso, id));
    }

    // Construtor que aceita apenas a mensagem (para uso mais flexível)
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
