package com.pedro.gestao_de_rh.hrms.repository;

import com.pedro.gestao_de_rh.hrms.model.SolicitacaoFerias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Interface Repository para operações de CRUD na entidade SolicitacaoFerias.
 */
@Repository
public interface SolicitacaoFeriasRepository extends JpaRepository<SolicitacaoFerias, Long> {
    // Queries customizadas serão adicionadas aqui conforme necessário.
}
