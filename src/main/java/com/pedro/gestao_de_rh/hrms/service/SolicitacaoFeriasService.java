package com.pedro.gestao_de_rh.hrms.service;

import com.pedro.gestao_de_rh.hrms.dto.funcionario.SolicitacaoDeFeriasDTO.SolicitacaoFeriasRequestDTO;
import com.pedro.gestao_de_rh.hrms.dto.funcionario.SolicitacaoDeFeriasDTO.SolicitacaoFeriasResponseDTO;
import com.pedro.gestao_de_rh.hrms.dto.funcionario.funcionarioDTO.FuncionarioResponseDTO;
import com.pedro.gestao_de_rh.hrms.enums.StatusFerias;
import com.pedro.gestao_de_rh.hrms.exception.RecursoNaoEncontradoException;
import com.pedro.gestao_de_rh.hrms.exception.RegraNegocioException;
import com.pedro.gestao_de_rh.hrms.model.Funcionario;
import com.pedro.gestao_de_rh.hrms.model.SolicitacaoFerias;
import com.pedro.gestao_de_rh.hrms.repository.SolicitacaoFeriasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Serviço responsável pela lógica de negócio das Solicitações de Férias.
 */
@Service
@RequiredArgsConstructor
public class SolicitacaoFeriasService {

    private final SolicitacaoFeriasRepository feriasRepository;
    private final FuncionarioService funcionarioService;

    // --- MÉTODOS DE CONVERSÃO INTERNOS ---

    /*
     * Converte a Entidade Funcionario para um FuncionarioResponseDTO simples.
     * Necessário para o DTO aninhado.
     */
    private FuncionarioResponseDTO toFuncionarioResponseDTO(Funcionario funcionario) {
        return FuncionarioResponseDTO.builder()
                .id(funcionario.getId())
                .nome(funcionario.getNome())
                // Adicione outros campos necessários do FuncionarioResponseDTO se existirem,
                // como cpf, dataNascimento, etc., que você usa na sua DTO de funcionário.
                .build();
    }

    /*
     * Converte a Entidade para o DTO de Resposta (USANDO OBJETO ANINHADO).
     */
    private SolicitacaoFeriasResponseDTO toResponseDTO(SolicitacaoFerias solicitacao) {
        Funcionario funcionarioEntity = solicitacao.getFuncionario();
        FuncionarioResponseDTO funcionarioDTO = null;

        if (funcionarioEntity != null) {
            // Converte a entidade Funcionario para o DTO aninhado
            funcionarioDTO = toFuncionarioResponseDTO(funcionarioEntity);
        }

        return SolicitacaoFeriasResponseDTO.builder()
                .id(solicitacao.getId())
                .dataInicio(solicitacao.getDataInicio())
                .dataFim(solicitacao.getDateFim())
                .status(solicitacao.getStatus().name()) // Converte Enum para String
                .funcionario(funcionarioDTO) // Seta o objeto FuncionarioResponseDTO
                .build();
    }

    /*
     * Converte o DTO de Requisição para a Entidade, definindo o status inicial.
     */
    private SolicitacaoFerias toEntity(SolicitacaoFeriasRequestDTO dto, Funcionario funcionario) {
        return SolicitacaoFerias.builder()
                .funcionario(funcionario)
                .dataInicio(dto.getDataInicio())
                .dateFim(dto.getDataFim())
                .status(StatusFerias.PENDENTE) // Sempre inicia como PENDENTE
                .build();
    }

    // --- LÓGICA DE NEGÓCIO ---

    /*
     * Método para criar uma nova solicitação de férias.
     */
    public SolicitacaoFeriasResponseDTO criarSolicitacao(SolicitacaoFeriasRequestDTO requestDTO) {
        // 1. Verificar se o funcionário existe
        // buscarFuncionarioPorId retorna a ENTIDADE Funcionario.
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(requestDTO.getFuncionarioId());

        // 2. Validação da Regra de Negócio: Data de Fim deve ser depois da Data de Início
        if (requestDTO.getDataFim().isBefore(requestDTO.getDataInicio())) {
            throw new RegraNegocioException("A data de fim das férias não pode ser anterior à data de início.");
        }

        // 3. Validação de período (mínimo de 1 dia)
        long dias = ChronoUnit.DAYS.between(requestDTO.getDataInicio(), requestDTO.getDataFim());
        if (dias <= 0) {
            throw new RegraNegocioException("O período de férias deve ter no mínimo 1 dia.");
        }

        // 4. Conversão e salvamento
        SolicitacaoFerias solicitacao = toEntity(requestDTO, funcionario);
        SolicitacaoFerias salvo = feriasRepository.save(solicitacao);

        return toResponseDTO(salvo);
    }


    public List<SolicitacaoFeriasResponseDTO> listarTodas() {
        return feriasRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public SolicitacaoFeriasResponseDTO buscarPorId(Long id) {
        SolicitacaoFerias solicitacao = feriasRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação de Férias", id));
        return toResponseDTO(solicitacao);
    }

    /*
     * Atualiza o status de uma solicitação de férias.
     * @param id ID da solicitação.
     * @param novoStatusString O novo status a ser aplicado (String).
     * @return DTO da solicitação atualizada.
     */
    public SolicitacaoFeriasResponseDTO atualizarStatus(Long id, String novoStatusString) {
        SolicitacaoFerias solicitacao = feriasRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação de Férias", id));

        // Converte a String para o Enum StatusFerias, tratando erros de formato.
        StatusFerias novoStatus;
        try {
            novoStatus = StatusFerias.valueOf(novoStatusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RegraNegocioException("Status inválido fornecido: " + novoStatusString + ". Use PENDENTE, APROVADA ou REJEITADA.");
        }

        // Aplica a regra de negócio
        solicitacao.setStatus(novoStatus);
        SolicitacaoFerias atualizada = feriasRepository.save(solicitacao);
        return toResponseDTO(atualizada);
    }

    public void deletarSolicitacao(Long id) {
        // Verifica se a solicitação existe antes de deletar
        feriasRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação de Férias", id));
        feriasRepository.deleteById(id);
    }
}
