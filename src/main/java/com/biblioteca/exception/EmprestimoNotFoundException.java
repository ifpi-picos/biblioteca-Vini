package com.biblioteca.exception;

public class EmprestimoNotFoundException extends RuntimeException {
    public EmprestimoNotFoundException(Long id) {
        super("Empréstimo não encontrado com ID: " + id);
    }
}