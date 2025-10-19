package com.pedro.gestao_de_rh.hrms.exception;

import com.pedro.gestao_de_rh.hrms.dto.erros.DetalheErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Classe que centraliza o tratamento de exceções globalmente na API.
 * Anotada com @RestControllerAdvice para capturar exceções de todos os Controllers.
 */
@RestControllerAdvice // Garante que este handler se aplique a todos os @Controllers
public class GlobalExceptionHandler {

    /*
     * Captura a exceção RecursoNaoEncontradoException e retorna HTTP 404 (Not Found).
     */
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<DetalheErroDTO> handleRecursoNaoEncontradoException(
            RecursoNaoEncontradoException ex, WebRequest request) {

        DetalheErroDTO erro = DetalheErroDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .erro("Not Found")
                .mensagem(ex.getMessage()) // Usa a mensagem formatada da exceção
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    /*
     * Captura exceções de validação (@Valid) e retorna HTTP 400 (Bad Request).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DetalheErroDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        // Extrai todos os erros de campo e formata em uma lista de strings
        List<String> errosDetalhes = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return fieldName + ": " + errorMessage;
                })
                .collect(Collectors.toList());

        DetalheErroDTO erro = DetalheErroDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Bad Request")
                .mensagem("Falha na validação dos campos da requisição.")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .detalhes(errosDetalhes) // Inclui a lista de erros de validação
                .build();

        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    /*
     * Tratamento genérico para outras exceções não mapeadas.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DetalheErroDTO> handleAllExceptions(Exception ex, WebRequest request) {

        DetalheErroDTO erro = DetalheErroDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .erro("Internal Server Error")
                .mensagem("Ocorreu um erro inesperado: " + ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}