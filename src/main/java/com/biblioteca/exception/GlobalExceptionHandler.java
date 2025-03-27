package com.biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(LivroNotFoundException.class)
    public ResponseEntity<String> handleLivroNotFound(LivroNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<String> handleUsuarioNotFound(UsuarioNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    @ExceptionHandler(EmprestimoNotFoundException.class)
    public ResponseEntity<String> handleEmprestimoNotFound(EmprestimoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    @ExceptionHandler(LivroIndisponivelException.class)
    public ResponseEntity<String> handleLivroIndisponivel(LivroIndisponivelException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
    @ExceptionHandler(EmprestimoJaDevolvidoException.class)
    public ResponseEntity<String> handleEmprestimoJaDevolvido(EmprestimoJaDevolvidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
    @ExceptionHandler(EmprestimoStrategyNotSetException.class)
    public ResponseEntity<String> handleEmprestimoStrategyNotSet(EmprestimoStrategyNotSetException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}