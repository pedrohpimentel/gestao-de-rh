package com.pedro.gestao_de_rh.hrms.service;

import com.pedro.gestao_de_rh.hrms.dto.funcionario.FuncionarioResponseDTO;
import com.pedro.gestao_de_rh.hrms.dto.funcionario.RegistroDePontoRequestDTO;
import com.pedro.gestao_de_rh.hrms.dto.funcionario.RegistroDePontoResponseDTO;
import com.pedro.gestao_de_rh.hrms.exception.RecursoNaoEncontradoException;
import com.pedro.gestao_de_rh.hrms.model.Funcionario;
import com.pedro.gestao_de_rh.hrms.model.RegistroDePonto; // Nome da Entidade Corrigido
import com.pedro.gestao_de_rh.hrms.repository.RegistroDePontoRepository; // Nome do Repository Corrigido
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço responsável pela lógica de negócio do Registro de Ponto.
 * Refatorada para utilizar DTOs na comunicação com o Controller.
 */
@Service
@RequiredArgsConstructor
public class RegistroDePontoService {

    private final RegistroDePontoRepository registroDePontoRepository; // Variável corrigida
    private final FuncionarioService funcionarioService;

    // --- MÉTODOS DE CONVERSÃO INTERNOS ---

    /**
     * Converte RegistroDePontoRequestDTO para a Entidade RegistroDePonto.
     */
    private RegistroDePonto toEntity(RegistroDePontoRequestDTO dto) {
        // Busca a Entidade Funcionario (lança 404 se não existir)
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(dto.getFuncionarioId());

        RegistroDePonto ponto = new RegistroDePonto(); // Instância corrigida
        ponto.setFuncionario(funcionario);
        ponto.setData(dto.getData());
        ponto.setEntrada(dto.getEntrada());
        ponto.setSaida(dto.getSaida());
        return ponto;
    }

    /*
     * Converte a Entidade RegistroDePonto para RegistroDePontoResponseDTO.
     */
    private RegistroDePontoResponseDTO toResponseDTO(RegistroDePonto ponto) { // Parâmetro corrigido
        // Usa o método DTO do FuncionarioService para evitar duplicação de conversão
        FuncionarioResponseDTO funcionarioDTO = funcionarioService.buscarFuncionarioPorIdDTO(ponto.getFuncionario().getId());

        return RegistroDePontoResponseDTO.builder() // Builder corrigido
                .id(ponto.getId())
                .data(ponto.getData())
                .entrada(ponto.getEntrada())
                .saida(ponto.getSaida())
                .funcionario(funcionarioDTO)
                .build();
    }

    // --- MÉTODOS DE SERVIÇO REFATORADOS ---

    /*
     * Registra um novo ponto a partir do Request DTO.
     */
    public RegistroDePontoResponseDTO registrarPonto(RegistroDePontoRequestDTO requestDTO) {
        RegistroDePonto ponto = toEntity(requestDTO); // Variável corrigida
        RegistroDePonto salvo = registroDePontoRepository.save(ponto); // Variável corrigida
        return toResponseDTO(salvo);
    }

    /*
     * Busca um ponto pelo ID e retorna o Response DTO.
     */
    public RegistroDePontoResponseDTO buscarPontoPorId(Long id) {
        RegistroDePonto ponto = registroDePontoRepository.findById(id) // Variável corrigida
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro de Ponto", id)); // Mensagem corrigida
        return toResponseDTO(ponto);
    }

    /*
     * Lista todos os pontos de um funcionário específico, retornando uma lista de Response DTOs.
     * Garante que o funcionário exista antes de buscar os pontos.
     */
    public List<RegistroDePontoResponseDTO> listarPontosPorFuncionario(Long funcionarioId) {
        // Garante a existência do funcionário (lança 404 se não existir)
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(funcionarioId);

        return registroDePontoRepository.findByFuncionarioId(funcionario.getId()).stream() // Variável corrigida
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
