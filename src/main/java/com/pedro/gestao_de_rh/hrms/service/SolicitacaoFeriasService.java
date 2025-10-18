package com.pedro.gestao_de_rh.hrms.service;

import com.pedro.gestao_de_rh.hrms.enums.StatusFerias;
import com.pedro.gestao_de_rh.hrms.exception.RecursoNaoEncontradoException;
import com.pedro.gestao_de_rh.hrms.model.Funcionario;
import com.pedro.gestao_de_rh.hrms.model.SolicitacaoFerias;
import com.pedro.gestao_de_rh.hrms.repository.SolicitacaoFeriasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Camada de serviço responsável pela lógica de negócio das Solicitações de Férias.
 * Usa @RequiredArgsConstructor para realizar Injeção de Dependência via Construtor.
 */

@Service
@RequiredArgsConstructor
public class SolicitacaoFeriasService {

    private final SolicitacaoFeriasRepository solicitacaoFeriasRepository;
    private final FuncionarioService funcionarioService;

    /*
      Submete uma nova solicitação de férias para um funcionário.
      O status inicial é definido como PENDENTE.
      @param funcionarioId O ID do funcionário solicitante.
      @param solicitacao O objeto SolicitacaoFerias com dataInicio e dataFim.
      @return A solicitação de férias salva.
      @throws RecursoNaoEncontradoException Se o funcionário não for encontrado.
     */

    public SolicitacaoFerias criarSolicitacao(Long funcionarioId, SolicitacaoFerias solicitacao) {
        // 1. Busca e valida a existência do funcionário (lançará 404 se não existir.
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(funcionarioId);

        // 2. Define o funcionário na solicitação
        solicitacao.setFuncionario(funcionario);

        // 3. Define o status inicial
        solicitacao.setStatus(StatusFerias.PENDENTE);

        // 4. Persiste no banco
        return solicitacaoFeriasRepository.save(solicitacao);
    }

    /*
     * Busca uma solicitação de férias pelo ID. Lança 404 se não for encontrada.
     * @param id O ID da solicitação.
     * @return A solicitação encontrada.
     * @throws RecursoNaoEncontradoException Se a solicitação não for encontrada.
     */
    public SolicitacaoFerias buscarSolicitacaoPorId(Long id) {
        return solicitacaoFeriasRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação de Férias", id));
    }

    /*
     * Lista todas as solicitações de férias (essencial para a gerência de RH).
     * @return Uma lista de todas as solicitações.
     */
    public List<SolicitacaoFerias> listarSolicitacoes() {
        return solicitacaoFeriasRepository.findAll();
    }

    /*
     * Atualiza o status de uma solicitação de férias.
     * @param id O ID da solicitação.
     * @param novoStatus O novo status (APROVADA ou REJEITADA).
     * @return A solicitação atualizada.
     */
    public SolicitacaoFerias atualizarStatus(Long id, StatusFerias novoStatus) {
        SolicitacaoFerias solicitacao = buscarSolicitacaoPorId(id); //Valida a existência

        // Regra de Negócio: Garante que o status só pode ser alterado para aprovação ou rejeição
        if (novoStatus.equals(StatusFerias.PENDENTE)) {
            throw new IllegalArgumentException("O status não pode ser revertido para PENDENTE via esta operação.");
        }
        solicitacao.setStatus(novoStatus);
        return solicitacaoFeriasRepository.save(solicitacao);
    }
}