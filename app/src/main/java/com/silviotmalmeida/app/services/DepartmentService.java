package com.silviotmalmeida.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silviotmalmeida.app.entities.Department;
import com.silviotmalmeida.app.repositories.DepartmentRepository;

// classe de serviço que realiza a comunicação entre o DepartmentListController e o DepartmentRepository
// registrando a classe como service
@Service
public class DepartmentService {

    // injetando o repository da entidade Department
    @Autowired
    private DepartmentRepository repository;

    // método que retorna todos os registros
    public List<Department> findAll() {

        System.out.println("findAll");
        return this.repository.findAll();
    }

    // método que retorna o registro do id selecionado
    public Department findById(Long id) {

        // obtendo o registro
        Optional<Department> obj = this.repository.findById(id);

        // retorna o registro, se existir
        return obj.get();
    }

}
