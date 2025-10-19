package com.pedro.gestao_de_rh.hrms.controller;

import com.pedro.gestao_de_rh.hrms.dto.funcionario.RegistroDePontoRequestDTO;
import com.pedro.gestao_de_rh.hrms.dto.funcionario.RegistroDePontoResponseDTO;
import com.pedro.gestao_de_rh.hrms.dto.ponto.TotalHorasTrabalhadasDTO;
import com.pedro.gestao_de_rh.hrms.service.RegistroDePontoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Controller responsável pelos endpoints da gestão de Registro de Ponto.
 */
@RestController
@RequestMapping("/ponto")
@AllArgsConstructor
public class RegistroDePontoController {

    private final RegistroDePontoService registroDePontoService;

    /*
     * Endpoint para criar um novo registro de ponto.
     * Usa @Valid para ativar as validações do DTO.
     */
    @PostMapping
    public ResponseEntity<RegistroDePontoResponseDTO> criarRegistro(@Valid @RequestBody RegistroDePontoRequestDTO requestDTO) {
        RegistroDePontoResponseDTO novoRegistro = registroDePontoService.criarRegistro(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRegistro);
    }

    /*
     * Endpoint para buscar todos os registros de ponto.
     */
    @GetMapping
    public ResponseEntity<List<RegistroDePontoResponseDTO>> buscarTodosRegistros() {
        List<RegistroDePontoResponseDTO> registros = registroDePontoService.buscarTodos();
        return ResponseEntity.ok(registros);
    }

    /*
     * Endpoint para buscar um registro de ponto específico por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegistroDePontoResponseDTO> buscarRegistroPorId(@PathVariable Long id) {
        RegistroDePontoResponseDTO registro = registroDePontoService.buscarPorId(id);
        return ResponseEntity.ok(registro);
    }

    /*
     * Endpoint para deletar um registro de ponto por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRegistro(@PathVariable Long id) {
        registroDePontoService.deletarRegistro(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * URI: /ponto/{funcionarioId}/horas/{ano}/{mes}
     */
    @GetMapping("/{funcionarioId}/horas/{ano}/{mes}")
    public ResponseEntity<TotalHorasTrabalhadasDTO> calcularHorasTrabalhadasMes(
            @PathVariable Long funcionarioId,
            @PathVariable int ano,
            @PathVariable int mes) {

        TotalHorasTrabalhadasDTO resultado = registroDePontoService.calcularHorasTrabalhadasMes(funcionarioId, ano, mes);
        return ResponseEntity.ok(resultado);
    }
}