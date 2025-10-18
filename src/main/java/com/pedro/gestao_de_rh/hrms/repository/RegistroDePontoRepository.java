package com.pedro.gestao_de_rh.hrms.repository;

import com.pedro.gestao_de_rh.hrms.model.RegistroDePonto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 Interface Repository para operações de CRUD na entidade RegistroDePonto.
 Herda de JpaRepository para ter acesso aos métodos de persistência (save, find, etc.).
 */

@Repository
public interface RegistroDePontoRepository extends JpaRepository<RegistroDePonto,Long> {
    // No futuro, podemos adicionar consultas personalizadas aqui, como:
    // List<RegistroDePonto> findByFuncionarioId(Long funcionarioId);
    // RegistroDePonto findTopByFuncionarioIdOrderByHorarioRegistroDesc(Long funcionarioId);
}
