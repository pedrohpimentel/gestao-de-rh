package com.pedro.gestao_de_rh.hrms.service;

import com.pedro.gestao_de_rh.hrms.enums.TipoRegistro;
import com.pedro.gestao_de_rh.hrms.model.Funcionario;
import com.pedro.gestao_de_rh.hrms.model.RegistroDePonto;
import com.pedro.gestao_de_rh.hrms.repository.RegistroDePontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistroDePontoService {

    private final RegistroDePontoRepository registroDePontoRepository;
    private final FuncionarioService funcionarioService;

    public RegistroDePonto registrarPonto(Long funcionarioId){
        // Verificar se o funcionário existe
        // Reutiliza o método do FuncionarioService. Se não existir, lança RecursoNaoEncontradoException (404).
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(funcionarioId);

        //Determinar o próximo tipo de registro (ENTRADA,SAIDA)
        TipoRegistro proximoTipo = determinarProximoTipo(funcionarioId);

        //Criar e salvar o novo registro
        RegistroDePonto novoRegistro = RegistroDePonto.builder()
                .funcionario(funcionario)
                .horaRegistro(LocalDateTime.now())
                .tipoRegistro(proximoTipo)
                .build();

        return registroDePontoRepository.save(novoRegistro);
    }

    /*
      Determina o próximo tipo de registro (ENTRADA ou SAIDA) baseado no último registro.
      Se não houver registros, o próximo é sempre ENTRADA.
      @param funcionarioId ID do funcionário.
      @return TipoRegistro (ENTRADA ou SAIDA).
     */

    private TipoRegistro determinarProximoTipo(Long funcionarioId){

        Optional<RegistroDePonto> ultimoRegistro = registroDePontoRepository.
                findTopByFuncionarioIdOrderByHorarioRegistroDes(funcionarioId);

        if (ultimoRegistro.isEmpty()){
            // Se não houver registro, o primeiro deve ser ENTRADA
            return TipoRegistro.ENTRADA;
        }

        // Se o último registro foi ENTRADA, o próximo é SAIDA. Se foi SAIDA, o próximo é ENTRADA.
        if (ultimoRegistro.get().getTipoRegistro() == TipoRegistro.ENTRADA){
            return TipoRegistro.SAIDA;
        } else {
            return TipoRegistro.ENTRADA;
        }
    }
}
