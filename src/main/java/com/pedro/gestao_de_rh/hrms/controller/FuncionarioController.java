package com.pedro.gestao_de_rh.hrms.controller;


import com.pedro.gestao_de_rh.hrms.model.Funcionario;
import com.pedro.gestao_de_rh.hrms.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
@RequiredArgsConstructor
public class FuncionarioController {


    private final FuncionarioService funcionarioService;

    // POST: Cria um novo funcionário. Retorna 201 Created e a entidade com o ID.
    @PostMapping
    ResponseEntity<Funcionario> salvarFuncionario(@RequestBody Funcionario funcionario){
        Funcionario novoFuncionario = funcionarioService.salvarFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFuncionario);
    }

    // GET: Lista todos os funcionários.
    @GetMapping
    public ResponseEntity<List<Funcionario>> listaFuncionarios(){
        List<Funcionario> funcionarios = funcionarioService.listaDeFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    // GET: Busca funcionário por ID. O ID é uma variável do path.
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarFuncionarioPorId (@PathVariable Long id){
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(id);
        return ResponseEntity.ok(funcionario);// Retorna 200 OK
        // Se não encontrar, o Service deve lançar uma exceção que resulta em 404
    }


    // PUT: Atualiza um funcionário por ID. O ID é uma variável do path.
    // Retornamos a entidade atualizada para o cliente (200 OK).
    @PutMapping("/{id}")
   public ResponseEntity<Funcionario> atualizarFuncionarioPorId (@PathVariable Long id,
            @RequestBody Funcionario funcionario){
        Funcionario funcionarioAtualizado = funcionarioService.atualizarFuncionarioPorId(id, funcionario);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    // DELETE: Deleta um funcionário por ID. Retorna 204 No Content.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionarioPorId(@PathVariable Long id){
        funcionarioService.deletarFuncionarioPorId(id);
        return ResponseEntity.noContent().build();// Retorna 204 No Content
    }

}
