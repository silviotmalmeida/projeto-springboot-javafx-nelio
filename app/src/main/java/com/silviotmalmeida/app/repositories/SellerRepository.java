package com.silviotmalmeida.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silviotmalmeida.app.entities.Seller;

// classe responsável por executar as operações da entidade Seller no BD
// as operações básicas já estão implementadas na superclasse JpaRepository
public interface SellerRepository extends JpaRepository<Seller, Long> {

}
