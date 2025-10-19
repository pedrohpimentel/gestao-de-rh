package com.pedro.gestao_de_rh.hrms.controller;

import com.pedro.gestao_de_rh.hrms.dto.funcionario.RegistroDePontoRequestDTO;
import com.pedro.gestao_de_rh.hrms.dto.funcionario.RegistroDePontoResponseDTO;
import com.pedro.gestao_de_rh.hrms.service.RegistroDePontoService; // Importação corrigida
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelos endpoints da gestão de Registro de Ponto.
 * Refatorado para usar DTOs.
 */
@RestController
@RequestMapping("/api/v1/pontos")
@RequiredArgsConstructor
public class RegistroDePontoController {

    private final RegistroDePontoService registroDePontoService; // Variável corrigida

    /*
     * POST /pontos
     * Registra um novo ponto.
     * Adicionada anotação @Valid.
     * @param requestDTO Dados do ponto a ser registrado.
     * @return 201 Created e o ponto registrado (ResponseDTO).
     */
    @PostMapping
    public ResponseEntity<RegistroDePontoResponseDTO> registrarPonto(@Valid @RequestBody RegistroDePontoRequestDTO requestDTO) {
        RegistroDePontoResponseDTO responseDTO = registroDePontoService.registrarPonto(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /*
     * GET /pontos/{id}
     * Busca um registro de ponto pelo ID.
     * @param id ID do ponto.
     * @return 200 OK e o ponto encontrado (ResponseDTO).
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegistroDePontoResponseDTO> buscarPontoPorId(@PathVariable Long id) {
        RegistroDePontoResponseDTO responseDTO = registroDePontoService.buscarPontoPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * GET /pontos/funcionario/{funcionarioId}
     * Lista todos os pontos de um funcionário.
     * @param funcionarioId ID do funcionário.
     * @return 200 OK e a lista de pontos (ResponseDTO).
     */
    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<RegistroDePontoResponseDTO>> listarPontosPorFuncionario(@PathVariable Long funcionarioId) {
        List<RegistroDePontoResponseDTO> responseDTOs = registroDePontoService.listarPontosPorFuncionario(funcionarioId);
        return ResponseEntity.ok(responseDTOs);
    }
}
