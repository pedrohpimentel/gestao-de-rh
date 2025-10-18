package com.pedro.gestao_de_rh.hrms.service;

import com.pedro.gestao_de_rh.hrms.exception.RecursoNaoEncontradoException;
import com.pedro.gestao_de_rh.hrms.model.Funcionario;
import com.pedro.gestao_de_rh.hrms.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public Funcionario salvarFuncionario(Funcionario funcionario){
        repository.saveAndFlush(funcionario);
        return funcionario;
    }

    public List<Funcionario> listaDeFuncionarios(){
        return repository.findAll();
    }

    //Busca o funcionário por ID, se não encontrar irá estourar a exceção RecursoNaoEncontradoException.
    public Funcionario buscarFuncionarioPorId(Long id){
        repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário com ID: " + id + " não encontrado!"));
        return buscarFuncionarioPorId(id);
    }

    public void deletarFuncionarioPorId(Long id){
        repository.deleteById(id);
    }

    //Atualiza o funcionário por ID, se não encontrar o ID irá estourar a exceção RecursoNaoEncontradoException.
    public Funcionario atualizarFuncionarioPorId(Long id, Funcionario funcionario){
        Funcionario funcionarioEntity = repository.findById(id).orElseThrow(() ->
        new RuntimeException("Funcionário não encontrado!"));

        Funcionario funcionarioAtualizado = Funcionario.builder()

                //Se um nome for fornecido (se for verdadeiro) na requisição, ele usa este novo valor "? funcionario.getNome()".
                //Se o nome da requisição for null (se for falso) ": funcionarioEntity.getNome()", ele vai usar o valor original
                //ja carregado pelo banco de dados (funcionarioEntity).
                .nome(funcionario.getNome() != null ? funcionario.getNome():
                        funcionarioEntity.getNome())

                //Mesma lógica para o campo cpf.
                .cpf(funcionario.getCpf() != null ? funcionario.getCpf():
                        funcionarioEntity.getCpf())

                //Mesma lógica para o campo cargo.
                .cargo(funcionario.getCargo() != null ? funcionario.getCargo():
                        funcionarioEntity.getCargo())

                //Mesma lógica para o campo de Data de Contratação.
                .dataContratacao(funcionario.getDataContratacao() != null ? funcionario.getDataContratacao():
                        funcionarioEntity.getDataContratacao()).
                id(funcionarioEntity.getId())//Pega o ID original da entidade carregada do banco (funcionarioEntity)
                // e atribui ao novo objeto funcionarioAtualizado.
                .build();

        //O método "save()" recebe o objeto funcionarioAtualizado.
        return repository.save(funcionarioAtualizado);
    }
}
