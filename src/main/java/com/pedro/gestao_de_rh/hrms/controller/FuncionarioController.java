package com.pedro.gestao_de_rh.hrms.controller;

import com.pedro.gestao_de_rh.hrms.dto.funcionario.funcionarioDTO.FuncionarioRequestDTO;
import com.pedro.gestao_de_rh.hrms.dto.funcionario.funcionarioDTO.FuncionarioResponseDTO;
import com.pedro.gestao_de_rh.hrms.exception.RecursoNaoEncontradoException;
import com.pedro.gestao_de_rh.hrms.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Controller responsável pelos endpoints da gestão de Funcionários.
 * Refatorado para usar DTOs.
 */
@RestController
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    /*
     * POST /funcionarios
     * Cria um novo funcionário.
     * Adicionada anotação @Valid.
     * @param requestDTO Dados do novo funcionário.
     * @return 201 Created e o funcionário criado (ResponseDTO).
     */
    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> criarFuncionario(@Valid @RequestBody FuncionarioRequestDTO requestDTO) {
        FuncionarioResponseDTO responseDTO = funcionarioService.criarFuncionario(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /*
     * GET /api/v1/funcionarios
     * Lista todos os funcionários.
     * @return 200 OK e a lista de funcionários (ResponseDTO).
     */
    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> listarTodos() {
        List<FuncionarioResponseDTO> responseDTOs = funcionarioService.listarTodos();
        return ResponseEntity.ok(responseDTOs);
    }

    /*
     * GET /api/v1/funcionarios/{id}
     * Busca um funcionário pelo ID.
     * Implementado try-catch para garantir o 404 localmente, contornando falha do Global Handler.
     * @param id ID do funcionário.
     * @return 200 OK e o funcionário encontrado (ResponseDTO) ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> buscarFuncionarioPorId(@PathVariable Long id) {
        try {
            FuncionarioResponseDTO responseDTO = funcionarioService.buscarFuncionarioPorIdDTO(id);
            return ResponseEntity.ok(responseDTO);
        } catch (RecursoNaoEncontradoException ex) {
            // Retorna 404 Not Found manualmente
            return ResponseEntity.notFound().build();
            // Nota: O Global Handler ainda deve cuidar do corpo do erro, mas o status é garantido aqui.
        }
    }

    /*
     * PUT /funcionarios/{id}
     * Atualiza os dados de um funcionário existente.
     * Adicionada anotação @Valid.
     * @param id ID do funcionário a ser atualizado.
     * @param requestDTO Dados atualizados.
     * @return 200 OK e o funcionário atualizado (ResponseDTO).
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizarFuncionario(
            @PathVariable Long id,
            @Valid @RequestBody FuncionarioRequestDTO requestDTO) {

        FuncionarioResponseDTO responseDTO = funcionarioService.atualizarFuncionario(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * DELETE /funcionarios/{id}
     * Deleta um funcionário pelo ID.
     * @param id ID do funcionário a ser deletado.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarFuncionario(@PathVariable Long id) {
        funcionarioService.deletarFuncionario(id);
    }
}
