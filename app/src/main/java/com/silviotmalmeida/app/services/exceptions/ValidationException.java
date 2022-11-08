package com.silviotmalmeida.app.services.exceptions;

import java.util.HashMap;
import java.util.Map;

// classe que representa as exceções personalizadas da camada de serviço
public class ValidationException extends RuntimeException {

    // atributo serial
    private static final long serialVersionUID = 1L;

    // declarando os pares chave-valor do formulário
    private Map<String, String> errors = new HashMap<>();

    // construtor
    public ValidationException(String msg) {
        super(msg);
    }

    // método que retorna os erros
    public Map<String, String> getErrors() {
        return errors;
    }

    // método que adiciona erros à coleção
    public void addError(String fieldName, String errorMessage) {
        this.errors.put(fieldName, errorMessage);
    }
}
