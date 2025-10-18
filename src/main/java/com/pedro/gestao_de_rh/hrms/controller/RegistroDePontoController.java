package com.pedro.gestao_de_rh.hrms.controller;

import com.pedro.gestao_de_rh.hrms.model.RegistroDePonto;
import com.pedro.gestao_de_rh.hrms.service.RegistroDePontoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class RegistroDePontoController {

    private RegistroDePontoService registroDePontoService;

    /*
     Endpoint para o funcionário bater o ponto.
     * Recebe o ID do funcionário como Path Variable.
     * A validação de existência do funcionário é feita dentro do Service.
     */

    @PostMapping("/bater/{funcionarioId}")
    public ResponseEntity<RegistroDePonto> baterPonto (@PathVariable Long funcionarioId){

        // Chama o Service, que contém toda a lógica:
        // 1. Verificar se o funcionário existe
        // 2. Determinar se é ENTRADA ou SAÍDA
        // 3. Salvar o registro

        RegistroDePonto novoRegistro = registroDePontoService.registrarPonto(funcionarioId);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoRegistro);
    }
}
