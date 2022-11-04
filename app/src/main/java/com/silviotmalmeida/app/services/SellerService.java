package com.silviotmalmeida.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silviotmalmeida.app.entities.Seller;
import com.silviotmalmeida.app.repositories.SellerRepository;

// classe de serviço que realiza a comunicação entre o SellerListController e o SellerRepository
// registrando a classe como service
@Service
public class SellerService {

    // injetando o repository da entidade Seller
    @Autowired
    private SellerRepository repository;

    // método que retorna todos os registros
    public List<Seller> findAll() {

        return this.repository.findAll();
    }

    // método que retorna o registro do id selecionado
    public Seller findById(Long id) {

        // obtendo o registro
        Optional<Seller> obj = this.repository.findById(id);

        // retorna o registro, se existir
        return obj.get();
    }

}
