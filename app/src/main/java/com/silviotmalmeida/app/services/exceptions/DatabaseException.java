package com.silviotmalmeida.app.services.exceptions;

// classe que representa as exceções personalizadas da camada de serviço
public class DatabaseException extends RuntimeException {

    // atributo serial
    private static final long serialVersionUID = 1L;

    // criando a exceção para os casos de violação de integridade do BD
    public DatabaseException(String msg) {
        super(msg);
    }
}
