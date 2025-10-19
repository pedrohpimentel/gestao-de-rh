package com.pedro.gestao_de_rh.hrms.service;

import com.pedro.gestao_de_rh.hrms.dto.funcionario.RegistroDePontoDTO.RegistroDePontoRequestDTO;
import com.pedro.gestao_de_rh.hrms.dto.funcionario.RegistroDePontoDTO.RegistroDePontoResponseDTO;
import com.pedro.gestao_de_rh.hrms.dto.ponto.TotalHorasTrabalhadasDTO;
import com.pedro.gestao_de_rh.hrms.exception.RecursoNaoEncontradoException;
import com.pedro.gestao_de_rh.hrms.model.Funcionario;
import com.pedro.gestao_de_rh.hrms.model.RegistroDePonto;
import com.pedro.gestao_de_rh.hrms.repository.FuncionarioRepository;
import com.pedro.gestao_de_rh.hrms.repository.RegistroDePontoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Serviço responsável pela lógica de negócio do Registro de Ponto.
 */
@Service
@AllArgsConstructor
public class RegistroDePontoService {

    private final RegistroDePontoRepository registroDePontoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioService funcionarioService; // Usado para buscar o Funcionário

    /*
     * Converte uma Entidade RegistroDePonto para um DTO de Resposta.
     * @param registro Entidade do Banco de Dados.
     * @return DTO formatado para a API.
     */
    private RegistroDePontoResponseDTO toResponseDTO(RegistroDePonto registro) {
        return RegistroDePontoResponseDTO.builder()
                .id(registro.getId())
                .funcionarioId(registro.getFuncionario().getId())
                .nomeFuncionario(registro.getFuncionario().getNome())
                .data(registro.getData())
                .entrada(registro.getEntrada())
                .saida(registro.getSaida())
                .build();
    }

    /*
     * Cria um novo registro de ponto no banco de dados.
     * @param requestDTO DTO de requisição com os dados do ponto.
     * @return DTO de resposta do registro criado.
     */
    public RegistroDePontoResponseDTO criarRegistro(RegistroDePontoRequestDTO requestDTO) {
        // 1. Verificar se o funcionário existe
        Funcionario funcionario = funcionarioRepository.findById(requestDTO.getFuncionarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionario", requestDTO.getFuncionarioId()));

        // 2. Converte DTO para Entidade
        RegistroDePonto novoRegistro = new RegistroDePonto();
        novoRegistro.setFuncionario(funcionario);
        novoRegistro.setData(requestDTO.getData());
        novoRegistro.setEntrada(requestDTO.getEntrada());
        novoRegistro.setSaida(requestDTO.getSaida()); // Pode ser null

        // 3. Salva e retorna o DTO de resposta
        RegistroDePonto salvo = registroDePontoRepository.save(novoRegistro);
        return toResponseDTO(salvo);
    }

    /*
     * Busca todos os registros de ponto.
     * @return Lista de DTOs de resposta.
     */
    public List<RegistroDePontoResponseDTO> buscarTodos() {
        return registroDePontoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /*
     * Busca um registro de ponto por ID.
     * @param id ID do registro.
     * @return DTO de resposta.
     */
    public RegistroDePontoResponseDTO buscarPorId(Long id) {
        RegistroDePonto registro = registroDePontoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("RegistroDePonto", id));
        return toResponseDTO(registro);
    }

    /*
     * Deleta um registro de ponto por ID.
     * @param id ID do registro a ser deletado.
     */
    public void deletarRegistro(Long id) {
        // Verifica se existe antes de deletar
        registroDePontoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("RegistroDePonto", id));

        registroDePontoRepository.deleteById(id);
    }


    /*
     * @param funcionarioId ID do funcionário.
     * @param ano O ano de referência.
     * @param mes O mês de referência (1-12).
     * @return DTO com o total de horas trabalhadas.
     */
    public TotalHorasTrabalhadasDTO calcularHorasTrabalhadasMes(Long funcionarioId, int ano, int mes) {
        // Verifica se o funcionário existe (reutilizando o serviço)
        funcionarioService.buscarFuncionarioPorId(funcionarioId); // Lança 404 se não existir

        // Define o período de busca (do primeiro ao último dia do mês)
        LocalDateTime inicioMes = LocalDateTime.of(ano, mes, 1, 0, 0);
        LocalDateTime fimMes = inicioMes.withDayOfMonth(inicioMes.toLocalDate().lengthOfMonth())
                .withHour(23).withMinute(59).withSecond(59);

        // Busca todos os registros de ponto do funcionário dentro do mês/ano
        List<RegistroDePonto> registros = registroDePontoRepository.findByFuncionarioIdAndDataBetween(
                funcionarioId, inicioMes.toLocalDate(), fimMes.toLocalDate());

        long totalMinutos = 0;

        // 4. Itera sobre os registros e calcula as horas
        for (RegistroDePonto registro : registros) {
            LocalTime entrada = registro.getEntrada();
            LocalTime saida = registro.getSaida();

            // Só calcula se o registro de ponto estiver completo (com saída)
            if (entrada != null && saida != null) {
                // Cria LocalDateTime para calcular a duração, pois Duration não trabalha com LocalTime diretamente
                // Assume que entrada e saída ocorreram no mesmo dia (registro.getData())
                LocalDateTime inicio = LocalDateTime.of(registro.getData(), entrada);
                LocalDateTime fim = LocalDateTime.of(registro.getData(), saida);

                // Garante que o cálculo seja positivo (caso a saída seja antes da entrada, o que deve ser evitado na UI/validação, mas seguro aqui)
                Duration duracao = Duration.between(inicio, fim).abs();
                totalMinutos += duracao.toMinutes();
            }
        }

        // 5. Formata o resultado para o DTO
        long horas = totalMinutos / 60;
        long minutos = totalMinutos % 60;

        return TotalHorasTrabalhadasDTO.builder()
                .funcionarioId(funcionarioId)
                .mes(mes)
                .ano(ano)
                .totalMinutos(totalMinutos)
                .totalHoras(String.format("%d:%02dh", horas, minutos)) // Formato H:MMh (Ex: 176:30h)
                .build();
    }
}