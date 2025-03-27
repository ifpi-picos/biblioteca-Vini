package com.biblioteca.controller;

import com.biblioteca.dto.LivroDTO;
import com.biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {
    
    private final LivroService livroService;
    
    @Autowired
    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }
    
    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<LivroDTO> cadastrar(@Valid @RequestBody LivroDTO livroDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(livroService.cadastrar(livroDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizar(@PathVariable Long id, 
                                            @Valid @RequestBody LivroDTO livroDTO) {
        return ResponseEntity.ok(livroService.atualizar(id, livroDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<LivroDTO>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(livroService.buscarPorTitulo(titulo));
    }
    
    @GetMapping("/disponiveis")
    public ResponseEntity<List<LivroDTO>> listarDisponiveis() {
        return ResponseEntity.ok(livroService.buscarDisponiveis());
    }
}