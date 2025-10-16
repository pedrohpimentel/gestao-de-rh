package com.pedro.gestao_de_rh.hrms.repository;

import com.pedro.gestao_de_rh.hrms.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario,Long> {
}
