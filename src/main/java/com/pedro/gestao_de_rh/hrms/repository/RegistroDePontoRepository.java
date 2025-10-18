package com.pedro.gestao_de_rh.hrms.repository;

import com.pedro.gestao_de_rh.hrms.model.RegistroDePonto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 Interface Repository para operações de CRUD na entidade RegistroDePonto.
 Herda de JpaRepository para ter acesso aos métodos de persistência (save, find, etc.).
 */

@Repository
public interface RegistroDePontoRepository extends JpaRepository<RegistroDePonto,Long> {
    /*
      Encontra o último registro de ponto de um funcionário.

        Regras do Spring Data JPA (Query Method):
      - findTop: Limita o resultado a 1 (o mais recente).
      - ByFuncionarioId: Filtra pelo ID do Funcionário.
      - OrderByHorarioRegistroDesc: Garante que o mais recente venha primeiro.

      @param funcionarioId ID do funcionário
      @return Optional contendo o último RegistroDePonto.
     */

    Optional<RegistroDePonto> findTopByFuncionarioIdOrderByHorarioRegistroDes(Long funcionarioId);
}
