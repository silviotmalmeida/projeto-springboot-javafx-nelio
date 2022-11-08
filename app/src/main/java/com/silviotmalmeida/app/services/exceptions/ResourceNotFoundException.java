package com.silviotmalmeida.app.services.exceptions;

// classe que representa as exceções personalizadas da camada de serviço
public class ResourceNotFoundException extends RuntimeException {

    // atributo serial
    private static final long serialVersionUID = 1L;

    // criando a exceção para os casos de id não encontrado
    public ResourceNotFoundException(Object id) {
        super("Resource not found. Id " + id);
    }
}
