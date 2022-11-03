package com.silviotmalmeida.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silviotmalmeida.app.entities.Department;

// classe responsável por executar as operações da entidade Department no BD
// as operações básicas já estão implementadas na superclasse JpaRepository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
