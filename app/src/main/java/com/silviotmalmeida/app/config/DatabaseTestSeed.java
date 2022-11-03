package com.silviotmalmeida.app.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.silviotmalmeida.app.entities.Department;
import com.silviotmalmeida.app.repositories.DepartmentRepository;

// classe que será utilizada para database seeding implementando o CommandLineRunner
@Configuration
@Profile("test")
public class DatabaseTestSeed implements CommandLineRunner {

    // injetando o repository da entidade User
    @Autowired
    private DepartmentRepository departmentRepository;

    // método da interface para permitir o database seeding no início da execução
    @Override
    public void run(String... args) throws Exception {

        // criando os departamentos
        Department d1 = new Department(null, "Books");
        Department d2 = new Department(null, "Computers");
        Department d3 = new Department(null, "Electronics");

        // salvando no BD
        departmentRepository.saveAll(Arrays.asList(d1, d2, d3));

        System.out.println(departmentRepository.findAll());
    }
}
