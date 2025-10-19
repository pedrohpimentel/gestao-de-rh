package com.pedro.gestao_de_rh.hrms.controller;

import com.pedro.gestao_de_rh.hrms.dto.ferias.FeriasStatusUpdateDTO;
import com.pedro.gestao_de_rh.hrms.dto.ferias.SolicitacaoFeriasRequestDTO;
import com.pedro.gestao_de_rh.hrms.dto.ferias.SolicitacaoFeriasResponseDTO;
import com.pedro.gestao_de_rh.hrms.enums.StatusFerias;
import com.pedro.gestao_de_rh.hrms.service.SolicitacaoFeriasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Controller responsável pelos endpoints de gestão de Solicitações de Férias.
 * Usa injeção de dependência via construtor (@RequiredArgsConstructor).
 * Mapeamento base: /ferias
 */
@RestController
@RequestMapping("/ferias")
@RequiredArgsConstructor
public class SolicitacaoFeriasController {

    private final SolicitacaoFeriasService solicitacaoFeriasService;

    /*
     * POST /ferias: Cria uma nova solicitação de férias.
     * @param requestDTO Dados da solicitação (funcionarioId, dataInicio, dataFim).
     * @return ResponseEntity com o objeto de férias criado e status 201.
     */
    @PostMapping
    public ResponseEntity<SolicitacaoFeriasResponseDTO> solicitarFerias(@RequestBody @Valid SolicitacaoFeriasRequestDTO requestDTO) {
        SolicitacaoFeriasResponseDTO novaFerias = solicitacaoFeriasService.criarSolicitacao(requestDTO);
        return new ResponseEntity<>(novaFerias, HttpStatus.CREATED);
    }

    /*
     * GET /ferias
     * Endpoint de gerência para listar todas as solicitações de férias existentes.
     * Mapeado para o método 'listarTodas' no Service.
     *
     * @return 200 OK e a lista de DTOs de solicitação.
     */
    @GetMapping
    public ResponseEntity<List<SolicitacaoFeriasResponseDTO>> listarTodasSolicitacoes(){
        // CORRIGIDO: Chamando o método 'listarTodas' que retorna List<SolicitacaoFeriasResponseDTO>
        List<SolicitacaoFeriasResponseDTO> solicitacoes = solicitacaoFeriasService.listarTodas();
        return ResponseEntity.ok(solicitacoes);
    }

    /*
     * GET /ferias/{id}
     * Busca uma solicitação específica.
     * Mapeado para o método 'buscarPorId' no Service.
     *
     * @param id ID da solicitação.
     * @return 200 OK e o DTO da solicitação.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoFeriasResponseDTO> buscarSolicitacaoPorId(@PathVariable Long id){
        // CORRIGIDO: Chamando o método 'buscarPorId' que retorna SolicitaçãoFeriasResponseDTO
        SolicitacaoFeriasResponseDTO solicitacao = solicitacaoFeriasService.buscarPorId(id);
        return ResponseEntity.ok(solicitacao);
    }

    /*
     * PUT /ferias/status/{id}
     * Endpoint de gerência para APROVAR ou REJEITAR uma solicitação de férias.
     *
     * @param id ID da solicitação a ser atualizada.
     * @param novoStatus O corpo da requisição contendo o novo StatusFerias.
     * @return 200 OK e o DTO da solicitação atualizada.
     */
    @PutMapping("/status/{id}")
    public ResponseEntity<SolicitacaoFeriasResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody FeriasStatusUpdateDTO updateDTO) { // Usa o novo DTO

        // Chamando o service com o valor String
        SolicitacaoFeriasResponseDTO solicitacaoAtualizada = solicitacaoFeriasService.atualizarStatus(id, updateDTO.getNovoStatus());
        return ResponseEntity.ok(solicitacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSolicitacao(@PathVariable Long id) {
        solicitacaoFeriasService.deletarSolicitacao(id);
        return ResponseEntity.noContent().build();
    }
}