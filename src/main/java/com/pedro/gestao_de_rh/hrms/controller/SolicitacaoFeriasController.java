package com.pedro.gestao_de_rh.hrms.controller;

import com.pedro.gestao_de_rh.hrms.enums.StatusFerias;
import com.pedro.gestao_de_rh.hrms.model.SolicitacaoFerias;
import com.pedro.gestao_de_rh.hrms.service.SolicitacaoFeriasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Controller responsável pelos endpoints de gestão de Solicitações de Férias.
 * Usa injeção de dependência via construtor (@RequiredArgsConstructor).
 * Mapeamento base: /api/v1/solicitacoes
 */
@RestController
@RequestMapping("/api/v1/solicitacoes")
@RequiredArgsConstructor
public class SolicitacaoFeriasController {

    private final SolicitacaoFeriasService solicitacaoFeriasService;

    /*
     * POST /solicitacoes/funcionario/{funcionarioId}
     * Endpoint para um funcionário submeter uma nova solicitação de férias.
     * O status inicial é definido como PENDENTE no Service.

     * @param funcionarioId ID do funcionário que está solicitando.
     * @param solicitacao O corpo da solicitação com dataInicio e dataFim.
     * @return 201 Created e a solicitação salva.
     */
    @PostMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<SolicitacaoFerias> criarSolicitacao(
            @PathVariable Long funcionarioId,
            @RequestBody SolicitacaoFerias solicitacao){

        SolicitacaoFerias novaSolicitacao = solicitacaoFeriasService.criarSolicitacao(funcionarioId, solicitacao);
        return new ResponseEntity<>(novaSolicitacao, HttpStatus.CREATED);
    }

    /*
     * GET /solicitacoes
     * Endpoint de gerência para listar todas as solicitações de férias existentes.
     *
     * @return 200 OK e a lista de solicitações.
     */
    @GetMapping
    public ResponseEntity<List<SolicitacaoFerias>> listarTodasSolicitacoes(){
        List<SolicitacaoFerias> solicitacoes = solicitacaoFeriasService.listarSolicitacoes();
        return ResponseEntity.ok(solicitacoes);
    }

    /*
     * GET /solicitacoes/{id}
     * Busca uma solicitação específica.
     *
     * @param id ID da solicitação.
     * @return 200 OK e a solicitação. Retorna 404 se não for encontrada (graças ao Advice).
     */
    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoFerias> buscarSolicitacaoPorId(@PathVariable Long id){
        SolicitacaoFerias solicitacao = solicitacaoFeriasService.buscarSolicitacaoPorId(id);
        return ResponseEntity.ok(solicitacao);
    }

    /*
     * PUT /solicitacoes/{id}/status
     * Endpoint de gerência para APROVAR ou REJEITAR uma solicitação de férias.
     * Espera no corpo da requisição o novo status (ex: "APROVADA" ou "REJEITADA").
     *
     * @param id ID da solicitação a ser atualizada.
     * @param novoStatus O corpo da requisição contendo o novo StatusFerias.
     * @return 200 OK e a solicitação atualizada.
     */
    @PutMapping("/{id/status}")
    public ResponseEntity<SolicitacaoFerias> atualizarStatus(
            @PathVariable Long id,
            @RequestBody StatusFerias novoStatus) { //O Spring converte o valor de body para enum

        SolicitacaoFerias solicitacaoAtualizada = solicitacaoFeriasService.atualizarStatus(id,novoStatus);
        return ResponseEntity.ok(solicitacaoAtualizada);
    }

}
