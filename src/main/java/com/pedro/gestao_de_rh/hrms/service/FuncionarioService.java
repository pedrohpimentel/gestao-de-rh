package com.pedro.gestao_de_rh.hrms.service;

import com.pedro.gestao_de_rh.hrms.dto.funcionario.funcionarioDTO.FuncionarioRequestDTO;
import com.pedro.gestao_de_rh.hrms.dto.funcionario.funcionarioDTO.FuncionarioResponseDTO;
import com.pedro.gestao_de_rh.hrms.exception.RecursoNaoEncontradoException;
import com.pedro.gestao_de_rh.hrms.model.Funcionario;
import com.pedro.gestao_de_rh.hrms.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Camada de serviço responsável pela lógica de negócio dos Funcionários.
 * Refatorada para utilizar DTOs na comunicação com o Controller.
 */
@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    // --- MÉTODOS DE CONVERSÃO INTERNOS ---

    /*
     * Converte FuncionarioRequestDTO para a Entidade Funcionario.
     */
    private Funcionario toEntity(FuncionarioRequestDTO dto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCargo(dto.getCargo());
        funcionario.setCpf(dto.getCpf());
        funcionario.setDataContratacao(dto.getDataContratacao());
        return funcionario;
    }

    /*
     * Converte a Entidade Funcionario para FuncionarioResponseDTO.
     */
    private FuncionarioResponseDTO toResponseDTO(Funcionario funcionario) {
        return FuncionarioResponseDTO.builder()
                .id(funcionario.getId())
                .nome(funcionario.getNome())
                .cargo(funcionario.getCargo())
                .cpf(funcionario.getCpf())
                .dataContratacao(funcionario.getDataContratacao())
                .build();
    }

    // --- MÉTODOS DE SERVIÇO REFATORADOS ---

    /*
     * Cria um novo funcionário a partir do Request DTO.
     */
    public FuncionarioResponseDTO criarFuncionario(FuncionarioRequestDTO requestDTO) {
        Funcionario funcionario = toEntity(requestDTO);
        Funcionario salvo = funcionarioRepository.save(funcionario);
        return toResponseDTO(salvo);
    }

    /*
     * Lista todos os funcionários, retornando uma lista de Response DTOs.
     */
    public List<FuncionarioResponseDTO> listarTodos() {
        return funcionarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /*
     * Busca um funcionário pelo ID e retorna o Response DTO.
     */
    public FuncionarioResponseDTO buscarFuncionarioPorIdDTO(Long id) {
        Funcionario funcionario = buscarFuncionarioPorId(id);
        return toResponseDTO(funcionario);
    }

    /*
     * Busca um funcionário pelo ID e retorna a ENTIDADE.
     * Método interno usado por outros Services (Ponto, Férias).
     */
    public Funcionario buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionario", id));
    }

    /*
     * Atualiza um funcionário existente a partir do Request DTO.
     */
    public FuncionarioResponseDTO atualizarFuncionario(Long id, FuncionarioRequestDTO requestDTO) {
        Funcionario funcionarioExistente = buscarFuncionarioPorId(id);

        // Atualiza os campos da Entidade existente com os dados do DTO
        funcionarioExistente.setNome(requestDTO.getNome());
        funcionarioExistente.setCargo(requestDTO.getCargo());
        funcionarioExistente.setCpf(requestDTO.getCpf());
        // Corrigido para dataContratacao
        funcionarioExistente.setDataContratacao(requestDTO.getDataContratacao());

        Funcionario salvo = funcionarioRepository.save(funcionarioExistente);
        return toResponseDTO(salvo);
    }

    /*
     * Deleta um funcionário pelo ID.
     */
    public void deletarFuncionario(Long id) {
        // 1. Busca o funcionário (para garantir que ele exista, senão lança a exceção 404)
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com ID: " + id));

        // 2. Chama a função de deleção. O CASCADE fará o resto!
        funcionarioRepository.delete(funcionario);
    }
}