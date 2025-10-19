package com.pedro.gestao_de_rh.hrms.exception;

/*
 * Exceção customizada para ser lançada quando uma regra de negócio específica
 * (como horários inválidos, saldo insuficiente, etc.) é violada.
 * Retorna geralmente HTTP 400 Bad Request.
 */
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String message) {
        super(message);
    }
}
