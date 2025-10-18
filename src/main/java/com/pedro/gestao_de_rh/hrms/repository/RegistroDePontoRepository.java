package com.pedro.gestao_de_rh.hrms.repository;

import com.pedro.gestao_de_rh.hrms.model.RegistroDePonto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroDePontoRepository extends JpaRepository<RegistroDePonto, Long> {

    /*
     * Busca todos os registros de ponto associados a um ID de funcionário específico.
     * @param funcionarioId O ID do funcionário.
     * @return Lista de registros de ponto.
     */
    List<RegistroDePonto> findByFuncionarioId(Long funcionarioId);
}
