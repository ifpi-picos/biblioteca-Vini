package com.biblioteca.controller;

import com.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {
    
    private final EmprestimoService emprestimoService;
    
    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }
    
    @PostMapping
    public ResponseEntity<EmprestimoDTO> realizarEmprestimo(
            @RequestParam Long livroId, 
            @RequestParam Long usuarioId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(emprestimoService.realizarEmprestimo(livroId, usuarioId));
    }
    
    @PutMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoDTO> devolverLivro(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.devolverLivro(id));
    }
    
    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listarTodos() {
        return ResponseEntity.ok(emprestimoService.listarTodos());
    }
    
    @GetMapping("/ativos")
    public ResponseEntity<List<EmprestimoDTO>> listarAtivos() {
        return ResponseEntity.ok(emprestimoService.listarAtivos());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EmprestimoDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(emprestimoService.listarPorUsuario(usuarioId));
    }
}