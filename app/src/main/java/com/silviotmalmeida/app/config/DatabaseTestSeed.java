package com.silviotmalmeida.app.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.silviotmalmeida.app.entities.Department;
import com.silviotmalmeida.app.entities.Seller;
import com.silviotmalmeida.app.repositories.DepartmentRepository;
import com.silviotmalmeida.app.repositories.SellerRepository;

// classe que será utilizada para database seeding implementando o CommandLineRunner
@Configuration
@Profile("test")
public class DatabaseTestSeed implements CommandLineRunner {

    // injetando o repository da entidade Department
    @Autowired
    private DepartmentRepository departmentRepository;

    // injetando o repository da entidade Seller
    @Autowired
    private SellerRepository sellerRepository;

    // método da interface para permitir o database seeding no início da execução
    @Override
    public void run(String... args) throws Exception {

        // criando os departamentos
        Department d1 = new Department(null, "Computers");
        Department d2 = new Department(null, "Electronics");
        Department d3 = new Department(null, "Fashion");
        Department d4 = new Department(null, "Books");

        // salvando no BD
        departmentRepository.saveAll(Arrays.asList(d1, d2, d3, d4));

        // criando os vendedores
        Seller s1 = new Seller(null, "Bob Brown", "bob@email.com", Instant.parse("1998-04-21T00:00:00Z"), 1000.0, d1);
        Seller s2 = new Seller(null, "Maria Green", "maria@email.com", Instant.parse("1979-12-31T00:00:00Z"), 3500.0,
                d2);
        Seller s3 = new Seller(null, "Alex Grey", "alex@email.com", Instant.parse("1988-01-15T00:00:00Z"), 2200.0, d1);
        Seller s4 = new Seller(null, "Martha Red", "martha@email.com", Instant.parse("1993-11-30T00:00:00Z"), 3000.0,
                d4);
        Seller s5 = new Seller(null, "Donald Blue", "donald@email.com", Instant.parse("2000-01-09T00:00:00Z"), 4000.0,
                d3);
        Seller s6 = new Seller(null, "Alex Pink", "pink@email.com", Instant.parse("1997-03-04T00:00:00Z"), 3000.0, d2);

        // salvando no BD
        sellerRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6));

        System.out.println(departmentRepository.findAll());

        System.out.println(sellerRepository.findAll());
    }
}
