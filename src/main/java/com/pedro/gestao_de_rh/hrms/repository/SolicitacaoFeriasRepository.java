package com.pedro.gestao_de_rh.hrms.repository;

import com.pedro.gestao_de_rh.hrms.enums.StatusFerias;
import com.pedro.gestao_de_rh.hrms.model.SolicitacaoFerias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Interface Repository para operações de CRUD na entidade SolicitacaoFerias.
 */
@Repository
public interface SolicitacaoFeriasRepository extends JpaRepository<SolicitacaoFerias, Long> {
    /*
     * Query Method para buscar todas as solicitações de férias com um status específico.
     * O Spring Data JPA traduz 'findByStatus' para uma consulta SQL.
     * @param status O StatusFerias (ex: PENDENTE).
     * @return Lista de solicitações que correspondem ao status.
     */
    List<SolicitacaoFerias> findByStatus (StatusFerias status);

    /*
     * Query Method otimizado para buscar todas as solicitações PENDENTES.
     * @return Lista de solicitações com Status.PENDENTE.
     */
    List<SolicitacaoFerias> findByStatusOrderByDataInicioAsc(StatusFerias status);
}